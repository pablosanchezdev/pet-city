package com.pablosanchezegido.petcity.features.login;

interface LoginInteractor {

    interface OnAuthFinishedListener {
        void onSuccess();
        void onError(String error);
    }

    boolean isUserLoggedIn();
    void loginUser(String email, String password, OnAuthFinishedListener listener);
}
