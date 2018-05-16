package com.pablosanchezegido.petcity.features.login;

public interface LoginPresenter {

    boolean isUserLoggedIn();
    void loginButtonClicked(String email, String pwd, int passwordMinLength);
    void registerClicked();
    void destroy();
}
