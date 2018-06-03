package com.pablosanchezegido.petcity.features.offers.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.offers.detail.OfferDetailActivity;
import com.pablosanchezegido.petcity.models.LatLng;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.utils.LocationManager;
import com.pablosanchezegido.petcity.utils.ModelMapperKt;
import com.pablosanchezegido.petcity.utils.PermissionUtilsKt;
import com.pablosanchezegido.petcity.utils.PreferencesManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersMapFragment extends Fragment implements OffersMapView, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private static final int LOCATION_PERMISSIONS_REQUEST_CODE = 1;

    @BindView(R.id.root_view) FrameLayout rootView;
    @BindView(R.id.pb) ProgressBar pb;

    private GoogleMap googleMap;

    private OffersMapPresenterImpl presenter;
    private double searchRadius;

    public OffersMapFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers_map, container, false);
        ButterKnife.bind(this, view);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        presenter = new OffersMapPresenterImpl(this, new OffersMapInteractorImpl());
        searchRadius = new PreferencesManager(getContext()).getSearchRadius();

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            checkLocationPermissions();
        }
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean somePermissionNotGranted = PermissionUtilsKt.checkGrantPermissionResults(grantResults);
                if (somePermissionNotGranted) {
                    locationPermissionNotGranted();
                } else {
                    locationPermissionGranted();
                }
            }
        }
    }

    @Override
    public void setProgressVisible(boolean visible) {
        pb.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void addMarker(double lat, double lng, String title, String id) {
        if (googleMap != null) {
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(new com.google.android.gms.maps.model.LatLng(lat, lng))
                    .title(title)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
            marker.setTag(id);
        }
    }

    @Override
    public void setViewPosition(List<LatLng> latLngs) {
        if (googleMap != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng latLng : latLngs) {
                builder.include(ModelMapperKt.latLngToLatLng(latLng));
            }
            LatLngBounds bounds = builder.build();

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
            googleMap.animateCamera(cu, 2000, null);
        }
    }

    @Override
    public void openOfferDetail(String id) {
        Intent offerDetailIntent = new Intent(getContext(), OfferDetailActivity.class);
        offerDetailIntent.putExtra(OfferDetailActivity.OFFER_ID, id);
        startActivity(offerDetailIntent);
    }

    @Override
    public void setError(String error) {
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setOnMarkerClickListener(this);
        this.googleMap = googleMap;
    }

    private void checkLocationPermissions() {
        String[] permissionsToRequest = PermissionUtilsKt.permissionsToRequest(getContext(),
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION});
        if (permissionsToRequest.length == 0) {  // All permissions are granted
            locationPermissionGranted();
        } else {
            requestPermissions(permissionsToRequest, LOCATION_PERMISSIONS_REQUEST_CODE);
        }
    }

    @SuppressLint("MissingPermission")
    private void locationPermissionGranted() {
        LocationManager.getLastLocation(getContext(), location -> {
            if (location != null) {
                presenter.fetchData(new LatLng(location.getLatitude(), location.getLongitude()), searchRadius);
            } else {
                presenter.fetchDataWithoutLocation();
            }
        });
    }

    private void locationPermissionNotGranted() {
        presenter.fetchDataWithoutLocation();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        presenter.offerRequested(marker.getTag().toString());
        return true;
    }
}
