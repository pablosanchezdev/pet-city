package com.pablosanchezegido.petcity.features.publish.place;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.publish.SlideRightSlideBottomTransitionActivity;
import com.pablosanchezegido.petcity.features.publish.dates.PublishDatesActivity;
import com.pablosanchezegido.petcity.features.publish.images.PublishImagesActivity;
import com.pablosanchezegido.petcity.features.publish.titledetail.PublishTitleDetailActivity;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.utils.PermissionUtilsKt;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class PublishPlaceActivity extends SlideRightSlideBottomTransitionActivity implements PublishPlaceView {

    public static final String PLACE_NAME = "placeName";
    public static final String PLACE_LAT = "placeLat";
    public static final String PLACE_LNG = "placeLng";
    //private static final int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final int PLAY_SERVICES_REPAIRABLE_EXCEPTION_CODE = 2;
    private static final int PLAY_SERVICES_NOT_AVAILABLE_EXCEPTION_CODE = 3;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int PLACE_PICK_REQUEST_CODE = 2;

    @BindView(R.id.root_view) ConstraintLayout rootView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_steps) TextView tvSteps;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.bt_next) Button btNext;

    @BindString(R.string.permissions_not_granted) String permissionsNotGranted;

    private PublishPlacePresenterImpl presenter;

    private String placeName;
    private double placeLat, placeLng;

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
    public void onButtonClicked(View view) {
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
        if (requestCode == PLACE_PICK_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (place.getAddress() != null) {
                    placeName = place.getAddress().toString();
                } else {
                    placeName = place.getName().toString();
                }
                placeLat = place.getLatLng().latitude;
                placeLng = place.getLatLng().longitude;
                presenter.placeSet(placeName);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                String error = getString(R.string.error_google_places, status.getStatusCode());
                showMessage(error);
            }
        }
    }

    @Override
    public void showChoosePlaceDialog() {
        String[] permissionsToRequest = PermissionUtilsKt.permissionsToRequest(this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION});

        if (permissionsToRequest.length == 0) {  // Permission is granted
            launchPlacePicker();
        } else {
            ActivityCompat.requestPermissions(this, permissionsToRequest, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean somePermissionNotGranted = PermissionUtilsKt.checkGrantPermissionResults(grantResults);
                if (somePermissionNotGranted) {
                    showMessage(permissionsNotGranted);
                } else {
                    launchPlacePicker();
                }
            }
        }
    }

    @Override
    public void setPlaceName(String address) {
        tvName.setText(address);
    }

    @Override
    public void setNextButtonVisible(boolean visible) {
        btNext.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void requestNextPage() {
        Intent nextActivityIntent = new Intent(this, PublishDatesActivity.class);
        Intent previousIntent = getIntent();
        nextActivityIntent.putExtra(PublishTitleDetailActivity.TITLE, previousIntent.getStringExtra(PublishTitleDetailActivity.TITLE));
        nextActivityIntent.putExtra(PublishTitleDetailActivity.DETAIL, previousIntent.getStringExtra(PublishTitleDetailActivity.DETAIL));
        nextActivityIntent.putExtra(PublishImagesActivity.FIRST_IMAGE_URI, previousIntent.getStringExtra(PublishImagesActivity.FIRST_IMAGE_URI));
        nextActivityIntent.putExtra(PublishImagesActivity.SECOND_IMAGE_URI, previousIntent.getStringExtra(PublishImagesActivity.SECOND_IMAGE_URI));
        nextActivityIntent.putExtra(PLACE_NAME, placeName);
        nextActivityIntent.putExtra(PLACE_LAT, placeLat);
        nextActivityIntent.putExtra(PLACE_LNG, placeLng);
        launchNextActivity(nextActivityIntent);
    }

    private void launchPlacePicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICK_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            int statusCode = e.getConnectionStatusCode();
            GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
            availability.getErrorDialog(this, statusCode, PLAY_SERVICES_REPAIRABLE_EXCEPTION_CODE);
        } catch (GooglePlayServicesNotAvailableException e) {
            int statusCode = e.errorCode;
            GoogleApiAvailability availability = GoogleApiAvailability.getInstance();
            availability.getErrorDialog(this, statusCode, PLAY_SERVICES_NOT_AVAILABLE_EXCEPTION_CODE);
        }
    }

    private void showMessage(String message) {
        ExtensionsKt.makeSnackbar(rootView, message, Snackbar.LENGTH_LONG);
    }
}
