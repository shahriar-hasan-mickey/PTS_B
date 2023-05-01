
package humble.slave.pts_b.features.model.data.twilioAccountData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SubresourceUris {

    @SerializedName("media")
    @Expose
    private String media;

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

}
