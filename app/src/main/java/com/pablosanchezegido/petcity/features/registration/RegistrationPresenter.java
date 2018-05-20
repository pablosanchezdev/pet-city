package com.pablosanchezegido.petcity.features.registration;

interface RegistrationPresenter {

    void birthDateClicked();
    void registerButtonClicked(String email, String password, int passWordMinLength, String fullName, String phoneNumber, String birthDate);
    void destroy();
}
