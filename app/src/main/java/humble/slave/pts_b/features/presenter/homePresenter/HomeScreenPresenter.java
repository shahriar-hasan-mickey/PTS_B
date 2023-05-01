package humble.slave.pts_b.features.presenter.homePresenter;

import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.appcompat.widget.SwitchCompat;

public interface HomeScreenPresenter {
    public void findSwitchState(String id, SwitchCompat toggleSwitch);

    public void updateOnChangeState(String id, Boolean switchOptionState);
    public Boolean isRecipientAdded(String id);

    public Boolean isSwitchedOn(String id);
}
