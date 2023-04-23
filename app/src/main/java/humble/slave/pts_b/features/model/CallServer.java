package humble.slave.pts_b.features.model;

import android.content.Context;

import humble.slave.pts_b.common.RequestCompleteListener;
import humble.slave.pts_b.features.model.data.serverData.HomeServerResponse;

public interface CallServer {
    void getTrafficAndTemperature(Context context, RequestCompleteListener<HomeServerResponse> callback);
}
