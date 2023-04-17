
package humble.slave.pts_b.features.model.data.serverData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class HomeServerResponse {

    @SerializedName("Message")
    @Expose
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
