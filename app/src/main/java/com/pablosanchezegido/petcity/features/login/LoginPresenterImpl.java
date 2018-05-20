package com.pablosanchezegido.petcity.features.login;

import android.util.Patterns;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;
    private AuthInteractor interactor;

    LoginPresenterImpl(LoginView view, AuthInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    private boolean validateEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean validatePassword(String password, int pwdMinLength) {
        return password.length() >= pwdMinLength;
    }

    private final AuthInteractor.OnAuthFinishedListener loginListener =
            new AuthInteractor.OnAuthFinishedListener() {
        @Override
        public void onSuccess() {
            view.setProgressIndicatorVisible(false);
            view.setLoginButtonEnabled(true);
            view.onLoginSuccess();
        }

        @Override
        public void onError(String error) {
            view.setProgressIndicatorVisible(false);
            view.onLoginError(error);
            view.setLoginButtonEnabled(true);
        }
    };

    @Override
    public boolean isUserLoggedIn() {
        return interactor.isUserLoggedIn();
    }

    @Override
    public void loginButtonClicked(String email, String pwd, int pwdMinLength) {
        if (!validateEmail(email)) {
            view.setEmailError();
            view.setEmailErrorVisible(true);
            view.setPasswordErrorVisible(false);
        } else if (!validatePassword(pwd, pwdMinLength)) {
            view.setPasswordError();
            view.setEmailErrorVisible(false);
            view.setPasswordErrorVisible(true);
        } else {
            view.setEmailErrorVisible(false);
            view.setPasswordErrorVisible(false);
            view.setProgressIndicatorVisible(true);
            view.setLoginButtonEnabled(false);
            interactor.loginUser(email, pwd, loginListener);
        }
    }

    @Override
    public void registerClicked() {
        view.onRegisterClicked();
    }

    @Override
    public void destroy() {
        view = null;
        interactor = null;
    }
}
