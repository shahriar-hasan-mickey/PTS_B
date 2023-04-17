
package humble.slave.pts_b.features.model.data.trafficData;

;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Departure {

    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("place")
    @Expose
    private Place place;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

}
