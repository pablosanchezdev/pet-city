package com.pablosanchezegido.petcity.features.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.login.AuthInteractorImpl;
import com.pablosanchezegido.petcity.features.login.LoginActivity;
import com.pablosanchezegido.petcity.features.offers.OffersFragment;
import com.pablosanchezegido.petcity.features.profile.ProfileFragment;
import com.pablosanchezegido.petcity.features.publish.PublishFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bnv)
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        bindListeners();

        if (savedInstanceState == null) {
            setFragment(new OffersFragment(), R.string.navigation_offers);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                setFragment(new OffersFragment(), R.string.navigation_offers);
                return true;
            case R.id.navigation_publish:
                setFragment(new PublishFragment(), R.string.navigation_publish);
                return true;
            case R.id.navigation_profile:
                setFragment(new ProfileFragment(), R.string.navigation_profile);
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                new AuthInteractorImpl().logoutUser();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void bindListeners() {
        bnv.setOnNavigationItemSelectedListener(this::onOptionsItemSelected);
    }

    private void setFragment(Fragment fragment, @StringRes int toolbarTitleRes) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(toolbarTitleRes);
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, fragment)
                .commit();
    }
}
