package com.pablosanchezegido.petcity.features.login;

public interface AuthInteractor {

    interface OnAuthFinishedListener {
        void onSuccess();
        void onError(String error);
    }

    boolean isUserLoggedIn();
    void loginUser(String email, String password, OnAuthFinishedListener listener);
    void registerUser(String email, String password, String fullName, String phoneNumber, OnAuthFinishedListener listener);
    void logoutUser();
}
