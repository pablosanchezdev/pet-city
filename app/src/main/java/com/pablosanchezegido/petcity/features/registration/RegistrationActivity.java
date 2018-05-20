package com.pablosanchezegido.petcity.features.registration;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pablosanchezegido.petcity.MainActivity;
import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.login.AuthInteractorImpl;
import com.pablosanchezegido.petcity.utils.CalendarUtilsKt;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.views.custom.CircularProgressButton;
import com.pablosanchezegido.petcity.views.dialogs.DatePickerFragment;

import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView, DatePickerFragment.DateSetListener {

    @BindView(R.id.root_view)
    LinearLayout rootView;

    @BindView(R.id.til_email)
    TextInputLayout tilEmail;

    @BindView(R.id.ed_email)
    TextInputEditText edEmail;

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;

    @BindView(R.id.ed_password)
    TextInputEditText edPassword;

    @BindView(R.id.til_name)
    TextInputLayout tilName;

    @BindView(R.id.ed_name)
    TextInputEditText edName;

    @BindView(R.id.til_phone_number)
    TextInputLayout tilPhoneNumber;

    @BindView(R.id.ed_phone_number)
    TextInputEditText edPhoneNumber;

    @BindView(R.id.til_birth_date)
    TextInputLayout tilBirthDate;

    @BindView(R.id.ed_birth_date)
    TextInputEditText edBirthDate;

    @BindView(R.id.bt_register)
    CircularProgressButton btRegister;

    @BindInt(R.integer.password_min_length)
    int passwordMinLength;

    @BindInt(R.integer.phone_number_length)
    int phoneNumberLength;

    @BindColor(R.color.primary_dark)
    int focusedColor;

    @BindColor(R.color.black)
    int unfocusedColor;

    @BindString(R.string.error_email)
    String emailError;

    @BindString(R.string.error_name)
    String nameError;

    @BindString(R.string.error_birth_date)
    String birthDateError;

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
            String birthDate = edBirthDate.getText().toString();
            presenter.registerButtonClicked(email, password, passwordMinLength, fullName, phoneNumber, birthDate);
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

    @OnClick(R.id.ed_birth_date)
    public void onBirthDateClicked() {
        presenter.birthDateClicked();
    }

    @OnEditorAction(R.id.ed_phone_number)
    public boolean onEditAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_GO) {
            presenter.birthDateClicked();
            return true;
        }

        return false;
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
    public void setBirthDateError(boolean error) {
        tilBirthDate.setError(error ? birthDateError : null);
    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    @Override
    public void showDatePickerDialog() {
        int year = CalendarUtilsKt.getCurrentYear();
        int month = CalendarUtilsKt.getCurrentMonth();
        int day = CalendarUtilsKt.getCurrentDay();
        DatePickerFragment dialog = DatePickerFragment.newInstance(year, month, day);
        dialog.show(getSupportFragmentManager(), dialog.getClass().getSimpleName());
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
        startActivity(mainActivityIntent);
        finish();
    }

    @Override
    public void onDateSet(int year, int month, int day) {
        edBirthDate.setText(CalendarUtilsKt.getDateFormatted(year, month, day));
    }
}
