package com.pablosanchezegido.petcity.features.registration;

public interface UserInteractor {

    interface OnUserCreatedListener {
        void onSuccess();
        void onError(String error);
    }

    void createUser(String id, String email, String name, String phoneNumber, String birthDate, OnUserCreatedListener listener);
}
