package geovis.services.dto;

public class XYZ implements Comparable<XYZ>{
    private Integer x;
    private Integer y;
    private Integer z;

    public XYZ(Integer x, Integer y, Integer z)  {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return x+","+y+","+z;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }

    @Override
    public int compareTo(XYZ o) {
        if(this.x.compareTo(o.x)!=0){
            return this.x.compareTo(o.x);
        }else {
            if(this.y.compareTo(o.y)!=0){
                return this.y.compareTo(o.y);
            }else {
                return  this.z.compareTo(o.z);
            }
        }

    }
}
