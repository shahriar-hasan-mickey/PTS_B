package humble.slave.pts_b.features.presenter.addAccount;

import android.widget.EditText;

public interface AccountPresenter {
    public void updateOnAddAccount(String SID, String TOKEN, String NUMBER);
    public void doesAccountExist(EditText SID, EditText TOKEN);
    public void removeAccount();
}
