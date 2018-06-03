package com.pablosanchezegido.petcity.features.registration;

import com.pablosanchezegido.petcity.features.common.AuthInteractor;

class RegistrationInteractorImpl implements RegistrationInteractor {

    RegistrationInteractorImpl() { }

    @Override
    public void registerUser(String email, String password, String fullName, String phoneNumber, OnAuthFinishedListener listener) {
        new AuthInteractor().registerUser(email, password, fullName, phoneNumber, new AuthInteractor.OnAuthFinishedListener() {
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
