
package humble.slave.pts_b.features.model.data.trafficData;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Route {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("sections")
    @Expose
    private List<Section> sections;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

}
