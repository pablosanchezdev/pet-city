package com.pablosanchezegido.petcity.features.login;

public interface LoginView {

    void shouldShowEmailError(boolean show);
    void setEmailError();
    void shouldShowPasswordError(boolean show);
    void setPasswordError();
    void shouldShowProgressIndicator(boolean show);
    void onError(String error);
    void onLoginSuccess();
    void onRegisterClicked();
}
