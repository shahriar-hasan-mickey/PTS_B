
package humble.slave.pts_b.features.model.data.trafficData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Place {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("originalLocation")
    @Expose
    private OriginalLocation originalLocation;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public OriginalLocation getOriginalLocation() {
        return originalLocation;
    }

    public void setOriginalLocation(OriginalLocation originalLocation) {
        this.originalLocation = originalLocation;
    }

}
