package com.pablosanchezegido.petcity.features.login;

public interface LoginView {

    void setEmailErrorVisible(boolean visible);
    void setEmailError();
    void setPasswordErrorVisible(boolean visible);
    void setPasswordError();
    void setProgressIndicatorVisible(boolean visible);
    void setLoginButtonEnabled(boolean enabled);
    void onError(String error);
    void onLoginSuccess();
    void onRegisterClicked();
}
