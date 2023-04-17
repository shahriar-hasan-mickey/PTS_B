
package humble.slave.pts_b.features.model.data.serverData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Set1 {

    @SerializedName("temperature")
    @Expose
    private String temperature;
    @SerializedName("traffic_motion")
    @Expose
    private String trafficMotion;
    @SerializedName("trffic_level")
    @Expose
    private String trfficLevel;

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getTrafficMotion() {
        return trafficMotion;
    }

    public void setTrafficMotion(String trafficMotion) {
        this.trafficMotion = trafficMotion;
    }

    public String getTrfficLevel() {
        return trfficLevel;
    }

    public void setTrfficLevel(String trfficLevel) {
        this.trfficLevel = trfficLevel;
    }

}
