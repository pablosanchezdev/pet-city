package com.pablosanchezegido.petcity.features.registration;

import com.pablosanchezegido.petcity.features.login.AuthInteractor;
import com.pablosanchezegido.petcity.utils.ValidationUtilsKt;

class RegistrationPresenterImpl implements RegistrationPresenter {

    private RegistrationView view;
    private AuthInteractor interactor;

    RegistrationPresenterImpl(RegistrationView view, AuthInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void birthDateClicked() {
        view.hideKeyboard();
        view.showDatePickerDialog();
    }

    @Override
    public void registerButtonClicked(String email, String password, int passWordMinLength, String fullName, String phoneNumber, String birthDate) {
        if (!ValidationUtilsKt.validateEmail(email)) {
            view.setEmailError(true);
            view.requestEmailFocus();
        } else if (!ValidationUtilsKt.validatePassword(password, passWordMinLength)) {
            view.setEmailError(false);
            view.setPasswordError(true);
            view.requestPasswordFocus();
        } else if (!ValidationUtilsKt.validateFullName(fullName)) {
            view.setEmailError(false);
            view.setPasswordError(false);
            view.setFullNameError(true);
            view.requestFullNameFocus();
        } else if (!ValidationUtilsKt.validatePhoneNumber(phoneNumber)) {
            view.setEmailError(false);
            view.setPasswordError(false);
            view.setFullNameError(false);
            view.setPhoneNumberError(true);
            view.requestPhoneNumberFocus();
        } else if (!ValidationUtilsKt.validateBirthDate(birthDate)) {
            view.setEmailError(false);
            view.setPasswordError(false);
            view.setFullNameError(false);
            view.setPhoneNumberError(false);
            view.setBirthDateError(true);
        } else {
            view.setEmailError(false);
            view.setPasswordError(false);
            view.setFullNameError(false);
            view.setPhoneNumberError(false);
            view.setBirthDateError(false);
            view.setProgressIndicatorVisible(true);
            view.setLoginButtonEnabled(false);
            interactor.registerUser(email, password, fullName, phoneNumber, birthDate, registerListener);
        }
    }

    @Override
    public void destroy() {
        view = null;
        interactor = null;
    }

    private AuthInteractor.OnAuthFinishedListener registerListener =
            new AuthInteractor.OnAuthFinishedListener() {
        @Override
        public void onSuccess() {
            view.setProgressIndicatorVisible(false);
            view.setLoginButtonEnabled(true);
            view.onRegisterSuccess();
        }

        @Override
        public void onError(String error) {
            view.setProgressIndicatorVisible(false);
            view.setLoginButtonEnabled(true);
            view.onRegisterError(error);
        }
    };
}
