package humble.slave.pts_b.features.model.CallServer;

import android.content.Context;

import java.util.Map;

import humble.slave.pts_b.common.RequestCompleteListener;
import humble.slave.pts_b.features.model.data.serverData.HomeServerResponse;
import humble.slave.pts_b.features.model.data.twilioAccountData.ResponseTwilio;

public interface CallServer {
    void getTrafficAndTemperature(Context context, RequestCompleteListener<HomeServerResponse> callback);
    void sendSMS(String SID, String base64EncodedCredentials, Map<String, String> data, RequestCompleteListener<ResponseTwilio> callback);
}
