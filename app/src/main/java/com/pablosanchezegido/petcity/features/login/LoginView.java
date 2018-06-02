package com.pablosanchezegido.petcity.features.login;

public interface LoginView {

    void setEmailError(boolean error);
    void requestEmailFocus();
    void setPasswordError(boolean error);
    void requestPasswordFocus();
    void setProgressIndicatorVisible(boolean visible);
    void setLoginButtonEnabled(boolean enabled);
    void onLoginError(String error);
    void onLoginSuccess();
    void onRegisterClicked();
}
