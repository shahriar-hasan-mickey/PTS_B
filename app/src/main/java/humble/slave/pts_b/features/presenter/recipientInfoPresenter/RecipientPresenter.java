package humble.slave.pts_b.features.presenter.recipientInfoPresenter;

import android.widget.EditText;

public interface RecipientPresenter {

    public void updateOnAddNumber(String number);
    public void doesNumberExist(EditText number);
    public void removeNumber();
}
