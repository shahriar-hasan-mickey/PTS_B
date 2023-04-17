
package humble.slave.pts_b.features.model.data.trafficData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Transport {

    @SerializedName("mode")
    @Expose
    private String mode;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

}
