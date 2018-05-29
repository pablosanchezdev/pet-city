package com.pablosanchezegido.petcity.features.publish.place;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.publish.SlideRightSlideBottomTransitionActivity;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;

import butterknife.BindView;
import butterknife.OnClick;

public class PublishPlaceActivity extends SlideRightSlideBottomTransitionActivity implements PublishPlaceView {
    
    private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @BindView(R.id.root_view) ConstraintLayout rootView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_steps) TextView tvSteps;
    @BindView(R.id.tv_address) TextView tvAddress;
    @BindView(R.id.bt_next) Button btNext;

    private PublishPlacePresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PublishPlacePresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_publish_place;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        tvSteps.setText("3/5");
    }

    @OnClick({R.id.bt_choose_place, R.id.bt_next})
    public void onChoosePlaceButtonClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_choose_place:
                presenter.choosePlace();
                break;
            case R.id.bt_next:
                presenter.nextPageRequested();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                presenter.placeSet(place.getAddress());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                String error = getString(R.string.error_google_places, status.getStatusCode());
                showMessage(error);
            }
        }
    }

    @Override
    public void showChoosePlaceDialog() {
        try {
            AutocompleteFilter filter = new AutocompleteFilter.Builder()
                    .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                    .build();
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                    .setFilter(filter)
                    .build(this);

            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            int statusCode = e.getConnectionStatusCode();
            GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
            availability.getErrorDialog(this, statusCode, 2);
        } catch (GooglePlayServicesNotAvailableException e) {
            int statusCode = e.errorCode;
            GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
            availability.getErrorDialog(this, statusCode, 2);
        }
    }

    @Override
    public void setPlaceAddress(CharSequence address) {
        tvAddress.setText(address);
    }

    @Override
    public void setNextButtonVisible(boolean visible) {
        btNext.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void requestNextPage() {

    }

    private void showMessage(String message) {
        ExtensionsKt.makeSnackbar(rootView, message, Snackbar.LENGTH_LONG);
    }
}
