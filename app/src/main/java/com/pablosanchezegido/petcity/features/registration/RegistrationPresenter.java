package com.pablosanchezegido.petcity.features.registration;

interface RegistrationPresenter {

    void registerButtonClicked(String email, String password, int passWordMinLength, String fullName, String phoneNumber);
    void destroy();
}
