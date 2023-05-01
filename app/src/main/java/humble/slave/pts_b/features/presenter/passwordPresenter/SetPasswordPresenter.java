package humble.slave.pts_b.features.presenter.passwordPresenter;

public interface SetPasswordPresenter {
    public void updateOnSetPassword(String password);
    public void isPasswordSet();
    public void checkPassword(String password);
    public void removePassword();
}
