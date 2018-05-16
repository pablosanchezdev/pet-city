package com.pablosanchezegido.petcity.features.login;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pablosanchezegido.petcity.MainActivity;
import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;

import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.root_view)
    RelativeLayout rootView;

    @BindView(R.id.ed_email)
    TextInputEditText edEmail;

    @BindView(R.id.tv_error_email)
    TextView tvErrorEmail;

    @BindView(R.id.ed_password)
    TextInputEditText edPassword;

    @BindView(R.id.tv_error_password)
    TextView tvErrorPassword;

    @BindInt(R.integer.password_min_length)
    int passwordMinLength;

    @BindView(R.id.tv_register)
    TextView tvRegister;

    @BindString(R.string.not_account)
    String notAccount;

    @BindColor(R.color.light_blue)
    int lightBlueColor;

    private ProgressBar progress;

    private LoginPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        presenter = new LoginPresenterImpl(this, new LoginInteractorImpl());
        ButterKnife.bind(this);
        initTvRegister();
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

    @OnClick(R.id.bt_login)
    public void onButtonClicked(View view) {
        String email = edEmail.getText().toString();
        String password = edPassword.getText().toString();
        presenter.loginButtonClicked(email, password, passwordMinLength);
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

    private void launchMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void shouldShowEmailError(boolean show) {
        tvErrorEmail.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setEmailError() {
        tvErrorEmail.setText(R.string.error_email);
    }

    @Override
    public void shouldShowPasswordError(boolean show) {
        tvErrorPassword.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void setPasswordError() {
        String error = getString(R.string.error_password, passwordMinLength);
        tvErrorPassword.setText(error);
    }

    @Override
    public void shouldShowProgressIndicator(boolean show) {
        if (show) {
            progress = ExtensionsKt.makeProgressBar(rootView);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            progress.setLayoutParams(params);
            rootView.addView(progress);
        } else {
            rootView.removeView(progress);
        }
    }

    @Override
    public void onError(String error) {
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onLoginSuccess() {
        launchMainActivity();
    }

    @Override
    public void onRegisterClicked() {

    }
}
