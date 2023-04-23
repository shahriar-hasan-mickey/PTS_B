package humble.slave.pts_b.common;

public interface RequestCompleteListener <T>{
    void onRequestSuccess(T data);
    void onRequestFailed(String errorMessage);
}
