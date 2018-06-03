package com.pablosanchezegido.petcity.features.login;

import com.pablosanchezegido.petcity.features.common.AuthInteractor;

class LoginInteractorImpl implements LoginInteractor {

    private AuthInteractor authInteractor;

    LoginInteractorImpl() {
        authInteractor = new AuthInteractor();
    }

    @Override
    public boolean isUserLoggedIn() {
        return authInteractor.isUserLoggedIn();
    }

    @Override
    public void loginUser(String email, String password, OnAuthFinishedListener listener) {
        authInteractor.loginUser(email, password, new AuthInteractor.OnAuthFinishedListener() {
            @Override
            public void onSuccess() {
                listener.onSuccess();
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }
}
