package com.pablosanchezegido.petcity.features.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.main.MainActivity;
import com.pablosanchezegido.petcity.utils.PreferencesManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity implements SettingsView {

    @BindView(R.id.ed_radius) EditText edRadius;
    @BindView(R.id.ed_num_max_recent_activity) EditText edNumMaxRecentActivity;

    private PreferencesManager prefManager;

    private SettingsPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ButterKnife.bind(this);

        prefManager = new PreferencesManager(this);

        presenter = new SettingsPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        presenter = null;
        super.onDestroy();
    }

    @OnClick(R.id.bt_save)
    public void onSaveSettingsClicked() {
        presenter.saveSettingsRequested();
    }

    @Override
    public void loadSettings() {
        edRadius.setText(String.valueOf(prefManager.getSearchRadius()));

        edNumMaxRecentActivity.setText(String.valueOf(prefManager.getNumMaxRecentActivity()));
    }

    @Override
    public void commitSettings() {
        float searchRadius = Float.parseFloat(edRadius.getText().toString());
        int numMaxRecentActivity = Integer.parseInt(edNumMaxRecentActivity.getText().toString());
        prefManager.saveSettings(searchRadius, numMaxRecentActivity);
        Intent mainActivityIntent = new Intent(this, MainActivity.class);
        mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivityIntent);
        finish();
    }
}
