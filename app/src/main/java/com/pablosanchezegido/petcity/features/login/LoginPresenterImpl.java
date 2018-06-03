package com.pablosanchezegido.petcity.features.login;

import com.pablosanchezegido.petcity.utils.ValidationUtilsKt;

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView view;
    private LoginInteractor interactor;

    LoginPresenterImpl(LoginView view, LoginInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public boolean isUserLoggedIn() {
        return interactor.isUserLoggedIn();
    }

    @Override
    public void loginButtonClicked(String email, String pwd, int pwdMinLength) {
        if (!ValidationUtilsKt.validateEmail(email)) {
            view.setEmailError(true);
            view.requestEmailFocus();
        } else if (!ValidationUtilsKt.validatePassword(pwd, pwdMinLength)) {
            view.setEmailError(false);
            view.setPasswordError(true);
            view.requestPasswordFocus();
        } else {
            view.setEmailError(false);
            view.setPasswordError(false);
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

    private final LoginInteractor.OnAuthFinishedListener loginListener =
            new LoginInteractor.OnAuthFinishedListener() {
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
}
