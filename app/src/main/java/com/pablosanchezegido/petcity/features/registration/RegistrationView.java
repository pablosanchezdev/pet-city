package com.pablosanchezegido.petcity.features.registration;

interface RegistrationView {

    void setEmailError(boolean error);
    void requestEmailFocus();
    void setPasswordError(boolean error);
    void requestPasswordFocus();
    void setFullNameError(boolean error);
    void requestFullNameFocus();
    void setPhoneNumberError(boolean error);
    void requestPhoneNumberFocus();
    void setProgressIndicatorVisible(boolean visible);
    void setLoginButtonEnabled(boolean enabled);
    void onRegisterError(String error);
    void onRegisterSuccess();
}
