
package humble.slave.pts_b.features.model.data.trafficData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Arrival {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("place")
    @Expose
    private Place__1 place;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Place__1 getPlace() {
        return place;
    }

    public void setPlace(Place__1 place) {
        this.place = place;
    }

}
