package humble.slave.pts_b.features.presenter;

import android.widget.Switch;

public interface SettingsStatespresenter {
    public void findSwitchState(String id, Switch toggleSwitch);

    public void updateOnChangeState(String id, Boolean switchOptionState);
}
