
package humble.slave.pts_b.features.model.data.trafficData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Place__1 {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("location")
    @Expose
    private Location__1 location;
    @SerializedName("originalLocation")
    @Expose
    private OriginalLocation__1 originalLocation;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location__1 getLocation() {
        return location;
    }

    public void setLocation(Location__1 location) {
        this.location = location;
    }

    public OriginalLocation__1 getOriginalLocation() {
        return originalLocation;
    }

    public void setOriginalLocation(OriginalLocation__1 originalLocation) {
        this.originalLocation = originalLocation;
    }

}
