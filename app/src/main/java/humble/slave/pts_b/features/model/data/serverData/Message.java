
package humble.slave.pts_b.features.model.data.serverData;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("set1")
    @Expose
    private Set1 set1;
    @SerializedName("set2")
    @Expose
    private Set2 set2;

    public Set1 getSet1() {
        return set1;
    }

    public void setSet1(Set1 set1) {
        this.set1 = set1;
    }

    public Set2 getSet2() {
        return set2;
    }

    public void setSet2(Set2 set2) {
        this.set2 = set2;
    }

}
