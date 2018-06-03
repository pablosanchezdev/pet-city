package com.pablosanchezegido.petcity.features.registration;

interface RegistrationInteractor {

    interface OnAuthFinishedListener {
        void onSuccess();
        void onError(String error);
    }

    void registerUser(String email, String password, String fullName, String phoneNumber, OnAuthFinishedListener listener);
}
