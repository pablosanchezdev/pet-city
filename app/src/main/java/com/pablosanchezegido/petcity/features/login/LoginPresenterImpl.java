package com.pablosanchezegido.petcity.features.login;

import android.util.Patterns;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;
    private LoginInteractor interactor;

    LoginPresenterImpl(LoginView view, LoginInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword(String password, int pwdMinLength) {
        return password.length() >= pwdMinLength;
    }

    private final LoginInteractor.OnAuthFinishedListener authListener =
            new LoginInteractor.OnAuthFinishedListener() {
        @Override
        public void onSuccess() {
            view.shouldShowProgressIndicator(false);
            view.onLoginSuccess();
        }

        @Override
        public void onError(String error) {
            view.shouldShowProgressIndicator(false);
            view.onError(error);
        }
    };

    @Override
    public boolean isUserLoggedIn() {
        return interactor.isUserLoggedIn();
    }

    @Override
    public void loginButtonClicked(String email, String pwd, int pwdMinLength) {
        if (!validateEmail(email)) {
            view.shouldShowEmailError(true);
            view.shouldShowPasswordError(false);
            view.setEmailError();
        } else if (!validatePassword(pwd, pwdMinLength)) {
            view.shouldShowEmailError(false);
            view.shouldShowPasswordError(true);
            view.setPasswordError();
        } else {
            view.shouldShowEmailError(false);
            view.shouldShowPasswordError(false);
            view.shouldShowProgressIndicator(true);
            interactor.authUser(LoginInteractor.AuthType.LOGIN, email, pwd, authListener);
        }
    }

    @Override
    public void registerClicked() {

    }

    @Override
    public void destroy() {
        view = null;
        interactor = null;
    }
}
