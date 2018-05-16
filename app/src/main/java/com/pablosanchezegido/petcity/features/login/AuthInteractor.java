package com.pablosanchezegido.petcity.features.login;

public interface AuthInteractor {

    enum AuthType {
        LOGIN,
        SIGNUP
    }

    interface OnAuthFinishedListener {
        void onSuccess();
        void onError(String error);
    }

    boolean isUserLoggedIn();
    void authUser(AuthType type, String email, String password, OnAuthFinishedListener listener);
}
