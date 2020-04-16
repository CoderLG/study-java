/* (c) 2018 Open Source Geospatial Foundation - all rights reserved
 * This code is licensed under the GPL 2.0 license, available at the root
 * application directory.
 */
package org.geoserver.wps.gs.download;

import java.awt.geom.AffineTransform;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.geoserver.catalog.Predicates;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.coverage.grid.io.DimensionDescriptor;
import org.geotools.coverage.grid.io.GranuleSource;
import org.geotools.coverage.grid.io.GridCoverage2DReader;
import org.geotools.coverage.grid.io.StructuredGridCoverage2DReader;
import org.geotools.coverage.util.FeatureUtilities;
import org.geotools.data.Query;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.geometry.GeneralEnvelope;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.geotools.referencing.operation.transform.ProjectiveTransform;
import org.geotools.util.factory.GeoTools;
import org.geotools.util.logging.Logging;
import org.locationtech.jts.geom.Geometry;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.feature.type.GeometryDescriptor;
import org.opengis.filter.Filter;
import org.opengis.filter.expression.PropertyName;
import org.opengis.geometry.BoundingBox;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.datum.PixelInCell;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

/**
 * A class delegated to return the proper GridGeometry to be used by Raster Download when target
 * size is not specified.
 */
class GridGeometryProvider {

    private static final Logger LOGGER = Logging.getLogger(GridGeometryProvider.class);

    /**
     * Class delegate to extract the resolution from a features collection based on the available
     * resolution related descriptors.
     */
    class ResolutionProvider {

        // Resolution descriptors
        private DimensionDescriptor resDescriptor;

        private DimensionDescriptor resXDescriptor;

        private DimensionDescriptor resYDescriptor;

        // CRS descriptor
        private DimensionDescriptor crsDescriptor;

        private boolean hasBothResolutions;

        private boolean isHeterogeneousCrs;

        private CRSRequestHandler crsRequestHandler;

        public ResolutionProvider(CRSRequestHandler crsRequestHandler) {
            this.crsRequestHandler = crsRequestHandler;
            Map<String, DimensionDescriptor> descriptors = crsRequestHandler.getDescriptors();
            resDescriptor = descriptors.get(DimensionDescriptor.RESOLUTION);
            resXDescriptor = descriptors.get(DimensionDescriptor.RESOLUTION_X);
            resYDescriptor = descriptors.get(DimensionDescriptor.RESOLUTION_Y);
            crsDescriptor = descriptors.get(DimensionDescriptor.CRS);
            hasBothResolutions = resXDescriptor != null && resYDescriptor != null;
            isHeterogeneousCrs = crsDescriptor != null;
        }

        /** No resolution can be provided if there isn't any resolution related descriptor */
        boolean canCompute() {
            return resDescriptor != null || (resXDescriptor != null && resYDescriptor != null);
        }

        /** Get the best resolution from the input {@link SimpleFeatureCollection}. */
        public ReferencedEnvelope getBestResolution(
                SimpleFeatureCollection features, final double[] bestResolution)
                throws FactoryException, TransformException, IOException {

            // Setting up features attributes to be checked
            final String resXAttribute =
                    hasBothResolutions
                            ? resXDescriptor.getStartAttribute()
                            : resDescriptor.getStartAttribute();
            final String resYAttribute =
                    hasBothResolutions
                            ? resYDescriptor.getStartAttribute()
                            : resDescriptor.getStartAttribute();

            String crsAttribute = isHeterogeneousCrs ? crsDescriptor.getStartAttribute() : null;
            SimpleFeatureType schema = features.getSchema();
            CoordinateReferenceSystem schemaCRS =
                    schema.getGeometryDescriptor().getCoordinateReferenceSystem();

            CoordinateReferenceSystem referenceCRS =
                    crsRequestHandler.canUseTargetCRSAsNative()
                            ? crsRequestHandler.getSelectedTargetCRS()
                            : schemaCRS;
            // Iterate over the features to extract the best resolution
            BoundingBox featureBBox = null;
            GeneralEnvelope env = null;
            ReferencedEnvelope envelope = null;
            double[] fallbackResolution =
                    new double[] {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};

            // Look for the best resolution available from features matching the targetCRS.
            // Keep also updating a secondary resolution from features not matching
            // the targetCRS to be used as fallback in case that none of the available
            // features is matching the requested CRS.
            try (SimpleFeatureIterator iterator = features.features()) {
                double[] res = new double[2];
                while (iterator.hasNext()) {
                    SimpleFeature feature = iterator.next();
                    extractResolution(
                            feature,
                            resXAttribute,
                            resYAttribute,
                            crsAttribute,
                            referenceCRS,
                            res,
                            fallbackResolution,
                            bestResolution);

                    featureBBox = computeBBox(feature, crsAttribute, schemaCRS);
                    // Update the accessed envelope
                    if (env == null) {
                        env = new GeneralEnvelope(featureBBox);
                    } else {
                        env.add(featureBBox);
                    }
                }
            }
            if (env != null) {
                envelope = new ReferencedEnvelope(env);
            }
            if (Double.isInfinite(bestResolution[0]) || Double.isInfinite(bestResolution[1])) {
                // There might be the case that no granules have been found having native CRS
                // matching the Target one, so no best resolution has been retrieved on that CRS.
                // Let's use the fallback resolution
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.fine(
                            "No granules are matching the targetCRS. Going to use fallback resolution:"
                                    + "\nresX="
                                    + fallbackResolution[0]
                                    + " resY="
                                    + fallbackResolution[1]);
                }
                bestResolution[0] = fallbackResolution[0];
                bestResolution[1] = fallbackResolution[1];
            }
            return envelope;
        }

        /**
         * compute the bbox of the provided feature, taking into account its native CRS if available
         */
        private BoundingBox computeBBox(
                SimpleFeature feature, String crsAttribute, CoordinateReferenceSystem schemaCRS)
                throws FactoryException, TransformException, IOException {
            ROIManager roiManager = crsRequestHandler.getRoiManager();
            if (roiManager != null && crsRequestHandler.canUseTargetCRSAsNative()) {
                String granuleCrsCode = (String) feature.getAttribute(crsAttribute);
                CoordinateReferenceSystem granuleCRS = crsRequestHandler.getCRS(granuleCrsCode);
                CoordinateReferenceSystem targetCRS = crsRequestHandler.getSelectedTargetCRS();
                Geometry geom = (Geometry) feature.getDefaultGeometry();
                MathTransform transform = CRS.findMathTransform(schemaCRS, targetCRS);
                if (CRS.equalsIgnoreMetadata(targetCRS, granuleCRS)) {
                    // The granule has same CRS as TargetCRS
                    // Do not reproject the boundingBox itself but let's
                    // reproject the geometry to get the native bbox
                    if (!transform.isIdentity()) {
                        geom = JTS.transform(geom, transform);
                    }
                    return JTS.bounds(geom, targetCRS);
                } else {
                    // Reproject the granule geometry to the requested CRS
                    return JTS.bounds(geom, granuleCRS).transform(targetCRS, true);
                }
            } else {
                // Classic behaviour
                return feature.getBounds();
            }
        }

        /** Extract the resolution from the specified feature via the resolution attributes. */
        private void extractResolution(
                SimpleFeature feature,
                String resXAttribute,
                String resYAttribute,
                String crsAttribute,
                CoordinateReferenceSystem referenceCRS,
                double[] resolution,
                double[] fallbackResolution,
                double[] bestResolution)
                throws FactoryException, TransformException, IOException {
            resolution[0] = (Double) feature.getAttribute(resXAttribute);
            resolution[1] =
                    hasBothResolutions
                            ? (Double) feature.getAttribute(resYAttribute)
                            : resolution[0];
            CoordinateReferenceSystem granuleCRS = null;
            if (isHeterogeneousCrs) {
                String crsId = (String) feature.getAttribute(crsAttribute);
                granuleCRS = crsRequestHandler.getCRS(crsId);
                transformResolution(feature, referenceCRS, granuleCRS, resolution);
            }
            boolean updateBest =
                    !crsRequestHandler.canUseBestResolutionOnMatchingCRS()
                            || CRS.equalsIgnoreMetadata(granuleCRS, referenceCRS);
            updateResolution(resolution, updateBest ? bestResolution : fallbackResolution);
        }

        private void updateResolution(double[] currentResolution, double[] storedResolution) {
            storedResolution[0] =
                    currentResolution[0] < storedResolution[0]
                            ? currentResolution[0]
                            : storedResolution[0];
            storedResolution[1] =
                    currentResolution[1] < storedResolution[1]
                            ? currentResolution[1]
                            : storedResolution[1];
        }

        /**
         * Compute the transformed resolution of the provided feature since we are in the case of
         * heterogeneous CRS.
         */
        private void transformResolution(
                SimpleFeature feature,
                CoordinateReferenceSystem schemaCRS,
                CoordinateReferenceSystem granuleCRS,
                double[] resolution)
                throws FactoryException, TransformException {
            MathTransform transform = CRS.findMathTransform(schemaCRS, granuleCRS);

            // Do nothing if the CRS transformation is the identity
            if (!transform.isIdentity()) {
                BoundingBox bounds = feature.getBounds();
                // Get the center coordinate in the granule's CRS
                double center[] =
                        new double[] {
                            (bounds.getMaxX() + bounds.getMinX()) / 2,
                            (bounds.getMaxY() + bounds.getMinY()) / 2
                        };

                MathTransform inverse = transform.inverse();
                transform.transform(center, 0, center, 0, 1);

                // Setup 2 segments in inputCrs
                double[] coords = new double[6];
                double[] resCoords = new double[6];

                // center
                coords[0] = center[0];
                coords[1] = center[1];

                // DX from center
                coords[2] = center[0] + resolution[0];
                coords[3] = center[1];

                // DY from center
                coords[4] = center[0];
                coords[5] = center[1] + resolution[1];

                // Transform the coordinates back to targetCrs
                inverse.transform(coords, 0, resCoords, 0, 3);

                double dx1 = resCoords[2] - resCoords[0];
                double dx2 = resCoords[3] - resCoords[1];
                double dy1 = resCoords[4] - resCoords[0];
                double dy2 = resCoords[5] - resCoords[1];

                // Computing euclidean distances
                double transformedDX = Math.sqrt(dx1 * dx1 + dx2 * dx2);
                double transformedDY = Math.sqrt(dy1 * dy1 + dy2 * dy2);
                resolution[0] = transformedDX;
                resolution[1] = transformedDY;
            }
        }
    }

    private CRSRequestHandler crsRequestHandler;

    public GridGeometryProvider(CRSRequestHandler crsRequestHandler) {
        this.crsRequestHandler = crsRequestHandler;
    }

    /**
     * Compute the requested GridGeometry taking into account resolution dimensions descriptor (if
     * any), specified filter and ROI
     */
    public GridGeometry2D getGridGeometry()
            throws TransformException, IOException, FactoryException {
        if (!crsRequestHandler.hasStructuredReader()) {

            //
            // CASE A: simple readers: return the native resolution gridGeometry
            //
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.fine("The underlying reader is not structured; returning native resolution");
            }
            return getNativeResolutionGridGeometry();
        } else {
            //
            // CASE B: StructuredGridCoverage2DReader
            //
            StructuredGridCoverage2DReader structuredReader =
                    crsRequestHandler.getStructuredReader();
            String coverageName = structuredReader.getGridCoverageNames()[0];

            ResolutionProvider provider = new ResolutionProvider(crsRequestHandler);

            //
            // Do we have any resolution descriptor available?
            // if not, go to standard computation.
            //
            if (!provider.canCompute()) {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.fine(
                            "The underlying reader is structured but no resolution descriptors are available"
                                    + ". Returning native resolution");
                }
                return getNativeResolutionGridGeometry();
            }

            GranuleSource granules = structuredReader.getGranules(coverageName, true);

            // Setup a query on top of ROI and input filter (if any)
            Query query = initQuery(granules);
            SimpleFeatureCollection features = granules.getGranules(query);

            if (features == null || features.isEmpty()) {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.fine(
                            "No features available for the specified query. Returning native resolution");
                }
                return getNativeResolutionGridGeometry();
            }
            // Initialize resolution with infinite numbers
            double[] resolution = new double[] {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
            ReferencedEnvelope envelope = provider.getBestResolution(features, resolution);
            AffineTransform at =
                    new AffineTransform(
                            resolution[0],
                            0,
                            0,
                            -resolution[1],
                            envelope.getMinX(),
                            envelope.getMaxY());
            MathTransform tx = ProjectiveTransform.create(at);

            return new GridGeometry2D(
                    PixelInCell.CELL_CORNER, tx, envelope, GeoTools.getDefaultHints());
        }
    }

    /**
     * Initialize the query to get granules, based on provided filter and region of interest (if
     * any)
     */
    private Query initQuery(GranuleSource granules)
            throws TransformException, FactoryException, IOException {
        List<Filter> filters = new ArrayList<Filter>();

        Query query = Query.ALL;

        // Set bbox query if a ROI has been provided
        ROIManager roiManager = crsRequestHandler.getRoiManager();
        if (roiManager != null) {
            CoordinateReferenceSystem targetCRS = roiManager.getTargetCRS();
            GeometryDescriptor geomDescriptor = granules.getSchema().getGeometryDescriptor();
            CoordinateReferenceSystem indexCRS = geomDescriptor.getCoordinateReferenceSystem();
            ReferencedEnvelope envelope = null;
            if (targetCRS != null
                    && (!roiManager.isRoiCrsEqualsTargetCrs()
                            || crsRequestHandler.canUseTargetCRSAsNative())) {
                envelope =
                        new ReferencedEnvelope(
                                roiManager.getSafeRoiInTargetCRS().getEnvelopeInternal(),
                                targetCRS);
                MathTransform reprojectionTrasform = null;
                if (!CRS.equalsIgnoreMetadata(targetCRS, indexCRS)) {
                    reprojectionTrasform = CRS.findMathTransform(targetCRS, indexCRS, true);
                    if (!reprojectionTrasform.isIdentity()) {
                        envelope = envelope.transform(indexCRS, true);
                    }
                }
            } else {
                envelope =
                        new ReferencedEnvelope(
                                roiManager.getSafeRoiInNativeCRS().getEnvelopeInternal(), indexCRS);
            }

            final PropertyName geometryProperty =
                    FeatureUtilities.DEFAULT_FILTER_FACTORY.property(geomDescriptor.getName());
            filters.add(FeatureUtilities.DEFAULT_FILTER_FACTORY.bbox(geometryProperty, envelope));
        }

        // Add the filter if specified
        Filter filter = crsRequestHandler.getFilter();
        if (filter != null) {
            filters.add(filter);
        }

        // Set the query filter
        query = new Query();
        query.setFilter(Predicates.and(filters));

        return query;
    }

    /** Default GridGeometry retrieval based on native resolution. */
    private GridGeometry2D getNativeResolutionGridGeometry() throws IOException {
        GridCoverage2DReader reader = crsRequestHandler.getReader();
        ROIManager roiManager = crsRequestHandler.getRoiManager();
        final ReferencedEnvelope roiEnvelope =
                roiManager != null
                        ? new ReferencedEnvelope(
                                roiManager.getSafeRoiInNativeCRS().getEnvelopeInternal(),
                                roiManager.getNativeCRS())
                        : null;
        ScaleToTarget scaling = new ScaleToTarget(reader, roiEnvelope);
        return scaling.getGridGeometry();
    }
}
