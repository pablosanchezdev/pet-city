package com.pablosanchezegido.petcity.features.registration;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.login.AuthInteractorImpl;
import com.pablosanchezegido.petcity.features.main.MainActivity;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.views.custom.CircularProgressButton;

import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {

    @BindView(R.id.root_view) LinearLayout rootView;
    @BindView(R.id.til_email) TextInputLayout tilEmail;
    @BindView(R.id.ed_email) TextInputEditText edEmail;
    @BindView(R.id.til_password) TextInputLayout tilPassword;
    @BindView(R.id.ed_password) TextInputEditText edPassword;
    @BindView(R.id.til_name) TextInputLayout tilName;
    @BindView(R.id.ed_name) TextInputEditText edName;
    @BindView(R.id.til_phone_number) TextInputLayout tilPhoneNumber;
    @BindView(R.id.ed_phone_number) TextInputEditText edPhoneNumber;
    @BindView(R.id.bt_register) CircularProgressButton btRegister;

    @BindInt(R.integer.password_min_length) int passwordMinLength;
    @BindInt(R.integer.phone_number_length) int phoneNumberLength;

    @BindColor(R.color.primary_dark) int focusedColor;
    @BindColor(R.color.black) int unfocusedColor;

    @BindString(R.string.error_email) String emailError;
    @BindString(R.string.error_name) String nameError;
    @BindString(R.string.error_birth_date) String birthDateError;

    RegistrationPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        presenter = new RegistrationPresenterImpl(this, new AuthInteractorImpl());

        ButterKnife.bind(this);
        bindListeners();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    private void bindListeners() {
        btRegister.setOnButtonClickListener(view -> {
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            String fullName = edName.getText().toString();
            String phoneNumber = edPhoneNumber.getText().toString();
            presenter.registerButtonClicked(email, password, passwordMinLength, fullName, phoneNumber);
        });
    }

    @OnFocusChange({R.id.ed_email, R.id.ed_password, R.id.ed_name, R.id.ed_phone_number})
    public void onEditTextFocusChanged(View view, boolean hasFocus) {
        TextInputEditText ed = (TextInputEditText) view;
        Drawable leftDrawable = ed.getCompoundDrawables()[0];

        if (leftDrawable != null) {
            int color = hasFocus ? focusedColor : unfocusedColor;
            leftDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public void setEmailError(boolean error) {
        tilEmail.setError(error ? emailError : null);
    }

    @Override
    public void requestEmailFocus() {
        tilEmail.requestFocus();
    }

    @Override
    public void setPasswordError(boolean error) {
        String passwordError = getString(R.string.error_password, passwordMinLength);
        tilPassword.setError(error ? passwordError : null);
    }

    @Override
    public void requestPasswordFocus() {
        tilPassword.requestFocus();
    }

    @Override
    public void setFullNameError(boolean error) {
        tilName.setError(error ? nameError : null);
    }

    @Override
    public void requestFullNameFocus() {
        tilName.requestFocus();
    }

    @Override
    public void setPhoneNumberError(boolean error) {
        String phoneNumberError = getString(R.string.error_phone_number, phoneNumberLength);
        tilPhoneNumber.setError(error ? phoneNumberError : null);
    }

    @Override
    public void requestPhoneNumberFocus() {
        tilPhoneNumber.requestFocus();
    }

    @Override
    public void setProgressIndicatorVisible(boolean visible) {
        btRegister.setProgressVisible(visible);
    }

    @Override
    public void setLoginButtonEnabled(boolean enabled) {
        btRegister.setButtonEnabled(enabled);
    }

    @Override
    public void onRegisterError(String error) {
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onRegisterSuccess() {
        Intent mainActivityIntent = new Intent(this, MainActivity.class);

        // Remove login activity from stack
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainActivityIntent);
        finish();
    }
}
