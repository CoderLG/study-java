package geovis.services.utils;


import geovis.services.dto.XYZ;
import javafx.geometry.BoundingBox;

public class TileUtil {


    /**
     * 经纬度转LRC
     * @param lat
     * @param lon
     * @param zoom
     * @return
     */
    public static XYZ getTileNumber(final double lat, final double lon, final int zoom) {

        int xtile = (int)Math.floor( (lon + 180) / 360 * (1<<(zoom+1)) ) ;
        int ytile = (int)Math.floor( (lat+90)/180*(1<<zoom));

        if (xtile < 0)
            xtile=0;
        if (xtile >= (1<<(zoom+1)))
            xtile=((1<<(zoom+1))-1);
        if (ytile < 0)
            ytile=0;
        if (ytile >= (1<<zoom))
            ytile=((1<<zoom)-1);

        return(new XYZ(xtile,ytile,zoom));
    }

    public static BoundingBox tile2boundingBox(XYZ xyz) {
        BoundingBox bb = new BoundingBox(tile2lat(xyz.getY() + 1, xyz.getZ()),tile2lat(xyz.getY(), xyz.getZ()),tile2lon(xyz.getX() + 1, xyz.getZ()),tile2lon(xyz.getX(), xyz.getZ()));
        return bb;
    }

    static double tile2lon(int x, int z) {
        return x / Math.pow(2.0, z+1) * 360.0 - 180;
    }

    static double tile2lat(int y, int z) {

        return y/Math.pow(2.0,z)*180 -90;
    }
}
