package com.pablosanchezegido.petcity.features.offers.list;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.offers.detail.OfferDetailActivity;
import com.pablosanchezegido.petcity.models.LatLng;
import com.pablosanchezegido.petcity.models.OfferView;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.utils.LocationManager;
import com.pablosanchezegido.petcity.utils.PermissionUtilsKt;
import com.pablosanchezegido.petcity.utils.PreferencesManager;
import com.pablosanchezegido.petcity.views.adapters.OffersAdapter;
import com.pablosanchezegido.petcity.views.custom.CircularProgressButton;
import com.pablosanchezegido.petcity.views.decorators.BigCardItemDecoration;
import com.pablosanchezegido.petcity.views.dialogs.AlertDialogFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersListFragment extends Fragment
        implements OffersListView, CircularProgressButton.OnButtonClickListener,
        AlertDialogFragment.OnAlertDialogClickListener {

    public static final float CARD_VIEW_HEIGHT_RATIO = 0.75f;
    private static final int LOCATION_PERMISSIONS_REQUEST_CODE = 1;

    @BindView(R.id.root_view) FrameLayout rootView;
    @BindView(R.id.pb) ProgressBar pb;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.bt_retry) CircularProgressButton btRetry;
    @BindView(R.id.tv_no_results) TextView tvNoResults;

    private OffersListPresenterImpl presenter;
    private double searchRadius;

    public OffersListFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        presenter = new OffersListPresenterImpl(this, new OffersListInteractorImpl());
        searchRadius = new PreferencesManager(getContext()).getSearchRadius();
        checkToShowLocationDialog();
        return view;
    }

    private void initViews() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.addItemDecoration(new BigCardItemDecoration(getActivity().getWindowManager(), CARD_VIEW_HEIGHT_RATIO));

        btRetry.setOnButtonClickListener(this);

        swipeRefresh.setColorSchemeColors(ContextCompat.getColor(getContext(), R.color.primary),
                ContextCompat.getColor(getContext(), R.color.primary_dark), ContextCompat.getColor(getContext(), R.color.accent));
        swipeRefresh.setOnRefreshListener(this::checkLocationPermissions);
    }

    private void checkToShowLocationDialog() {
        PreferencesManager prefManager = new PreferencesManager(getContext());
        if (prefManager.isFirstTimeLaunched()) {
            AlertDialogFragment alertDialog = AlertDialogFragment.newInstance(R.string.location_request_title,R.string.location_request_detail);
            alertDialog.show(getChildFragmentManager(), alertDialog.getClass().getSimpleName());
            alertDialog.setOnAlertDialogClickListener(this);
            prefManager.setIsFirstTimeLaunched(false);
        } else {
            checkLocationPermissions();
        }
    }

    @Override
    public void setProgressVisible(boolean visible) {
        pb.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setRetryButtonVisible(boolean visible) {
        btRetry.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setNoResultsVisible(boolean visible) {
        tvNoResults.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void layoutData(List<OfferView> offers) {
        btRetry.setProgressVisible(false);
        swipeRefresh.setRefreshing(false);
        rv.setAdapter(new OffersAdapter(offers, getActivity().getWindowManager(), listener));
    }

    @Override
    public void setError(String error) {
        btRetry.setProgressVisible(false);
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void openItemDetail(String itemId) {
        Intent detailIntent = new Intent(getContext(), OfferDetailActivity.class);
        detailIntent.putExtra(OfferDetailActivity.OFFER_ID, itemId);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
        ActivityCompat.startActivity(getContext(), detailIntent, options.toBundle());
    }

    @Override
    public void onButtonClick(View view) {
        btRetry.setProgressVisible(true);
        presenter.retryButtonClicked();
    }

    private OffersAdapter.OnItemClickListener listener = itemId -> presenter.itemRequested(itemId);

    @Override
    public void onPositiveButtonClick() {
        checkLocationPermissions();
    }

    @Override
    public void onNegativeButtonClick() {
        locationPermissionNotGranted();
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
}
