package com.pablosanchezegido.petcity.features.login;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pablosanchezegido.petcity.features.main.MainActivity;
import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.registration.RegistrationActivity;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.views.custom.CircularProgressButton;

import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnFocusChange;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.root_view)
    LinearLayout rootView;

    @BindView(R.id.ed_email)
    TextInputEditText edEmail;

    @BindView(R.id.tv_error_email)
    TextView tvErrorEmail;

    @BindView(R.id.ed_password)
    TextInputEditText edPassword;

    @BindView(R.id.tv_error_password)
    TextView tvErrorPassword;

    @BindView(R.id.bt_login)
    CircularProgressButton btLogin;

    @BindInt(R.integer.password_min_length)
    int passwordMinLength;

    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindString(R.string.not_account)
    String notAccount;

    @BindColor(R.color.primary_dark)
    int focusedColor;

    @BindColor(R.color.black)
    int unfocusedColor;

    private LoginPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenterImpl(this, new AuthInteractorImpl());

        ButterKnife.bind(this);
        initTvRegister();
        bindListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (presenter.isUserLoggedIn()) {
            launchMainActivity();
        }
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    private void initTvRegister() {
        Spannable spannableString = new SpannableString(notAccount);

        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                presenter.registerClicked();
            }
        };

        int start = notAccount.lastIndexOf(" ") + 1;
        int end = notAccount.length();
        spannableString.setSpan(clickSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        tvRegister.setText(spannableString);
        tvRegister.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @OnFocusChange({R.id.ed_email, R.id.ed_password})
    public void onEditTextFocusChanged(View view, boolean hasFocus) {
        TextInputEditText ed = (TextInputEditText) view;
        Drawable leftDrawable = ed.getCompoundDrawables()[0];

        if (leftDrawable != null) {
            int color = hasFocus ? focusedColor : unfocusedColor;
            leftDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void bindListeners() {
        btLogin.setOnButtonClickListener(view -> {
            String email = edEmail.getText().toString();
            String password = edPassword.getText().toString();
            presenter.loginButtonClicked(email, password, passwordMinLength);
        });
    }

    private void launchMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void setEmailErrorVisible(boolean visible) {
        tvErrorEmail.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setEmailError() {
        tvErrorEmail.setText(R.string.error_email);
    }

    @Override
    public void setPasswordErrorVisible(boolean visible) {
        tvErrorPassword.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setPasswordError() {
        String error = getString(R.string.error_password, passwordMinLength);
        tvErrorPassword.setText(error);
    }

    @Override
    public void setProgressIndicatorVisible(boolean visible) {
        btLogin.setProgressVisible(visible);
    }

    @Override
    public void setLoginButtonEnabled(boolean enabled) {
        btLogin.setButtonEnabled(enabled);
    }

    @Override
    public void onLoginError(String error) {
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onLoginSuccess() {
        launchMainActivity();
    }

    @Override
    public void onRegisterClicked() {
        Intent registerIntent = new Intent(this, RegistrationActivity.class);
        startActivity(registerIntent);
    }
}
