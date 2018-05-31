package com.pablosanchezegido.petcity.features.offers.detail;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.main.MainActivity;
import com.pablosanchezegido.petcity.utils.CalendarUtilsKt;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.utils.SpannableFactoryKt;
import com.pablosanchezegido.petcity.views.adapters.ImagePagerAdapter;
import com.pablosanchezegido.petcity.views.dialogs.AlertDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class OfferDetailActivity extends AppCompatActivity
        implements OfferDetailView, AppBarLayout.OnOffsetChangedListener, OnMapReadyCallback, AlertDialogFragment.OnAlertDialogClickListener {

    public static final String OFFER_ID = "offerId";
    private static final float COLLAPSING_RELATIVE_HEIGHT = 0.8f;
    private static final float MAP_ZOOM = 15.0f;

    @BindView(R.id.root_view) LinearLayout rootView;
    @BindView(R.id.appbar_layout) AppBarLayout appBarLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.view_pager) ViewPager viewPager;
    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_dates) TextView tvDates;
    @BindView(R.id.tv_detail) TextView tvDetail;
    @BindView(R.id.civ_image) CircleImageView civImage;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.ctv_dogs) CheckedTextView ctvDogs;
    @BindView(R.id.ctv_cats) CheckedTextView ctvCats;
    @BindView(R.id.tv_num_pets) TextView tvNumPets;
    @BindView(R.id.tv_place) TextView tvPlace;
    @BindView(R.id.bt_accept_offer) Button btAcceptOffer;
    @BindView(R.id.pb) ProgressBar pb;

    @BindString(R.string.accept_offer_successfully) String offerAcceptedSuccessfully;

    private GoogleMap googleMap;

    private OfferDetailPresenterImpl presenter;

    private String offerTitle, offerDates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);
        ButterKnife.bind(this);

        presenter = new OfferDetailPresenterImpl(this, new OfferDetailInteractorImpl());
        presenter.fetchOfferDetail(getIntent().getStringExtra(OFFER_ID));

        initViews();
        offerTitle = offerDates = "";
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    private void initViews() {
        initToolbar();
        initAppBarLayout();
        initMap();
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
    }

    private void initAppBarLayout() {
        final View parent = (View) appBarLayout.getParent();
        parent.getViewTreeObserver()
                .addOnGlobalLayoutListener(() -> appBarLayout.getLayoutParams().height =
                        (int) (parent.getMeasuredWidth() * COLLAPSING_RELATIVE_HEIGHT));

        appBarLayout.addOnOffsetChangedListener(this);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @OnClick(R.id.bt_accept_offer)
    public void onAcceptOfferButtonClicked() {
        presenter.acceptOfferRequested();
    }

    @Override
    public void setProgressVisible(boolean visible) {
        pb.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOfferImages(List<String> imageUrls) {
        ImagePagerAdapter adapter = new ImagePagerAdapter(imageUrls);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setOfferTitle(String title) {
        tvTitle.setText(title);
        this.offerTitle = title;
    }

    @Override
    public void setOfferDates(long startDate, long endDate) {
        String start = CalendarUtilsKt.getDateFromTimestamp(startDate, null);
        String end = CalendarUtilsKt.getDateFromTimestamp(endDate, null);
        String dates = getString(R.string.offer_date, start, end);
        tvDates.setText(dates);
        this.offerDates = dates;
    }

    @Override
    public void setOfferDetail(String detail) {
        tvDetail.setText(detail);
    }

    @Override
    public void setUserImage(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .error(R.drawable.ic_person)
                .into(civImage);
    }

    @Override
    public void setUserName(String name) {
        tvName.setText(name);
    }

    @Override
    public void setDogsVisible(boolean visible) {
        ctvDogs.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setCatsVisible(boolean visible) {
        ctvCats.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setOfferNumPets(int numPets) {
        String pets = getString(R.string.num_pets_holded, numPets);
        int index = pets.lastIndexOf(" ");
        SpannableString spannable = SpannableFactoryKt.makeRelativeSizeSpan(pets, 1.4f, index, pets.length());
        spannable.setSpan(new StyleSpan(Typeface.BOLD), index, pets.length(), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvNumPets.setText(spannable);
    }

    @Override
    public void setOfferPlace(String place) {
        tvPlace.setText(place);
    }

    @Override
    public void setOfferLocation(double lat, double lng) {
        addMarkerToMapAndMove(lat, lng);
    }

    @Override
    public void setOfferPrice(double price) {
        String priceText = getString(R.string.accept_offer, price);
        btAcceptOffer.setText(priceText);
    }

    @Override
    public void setOfferAcceptedSuccessfuly() {
        ExtensionsKt.makeSnackbar(rootView, offerAcceptedSuccessfully, Snackbar.LENGTH_LONG);
        new Handler().postDelayed(() -> {
            Intent mainActivityIntent = new Intent(OfferDetailActivity.this, MainActivity.class);
            startActivity(mainActivityIntent);
            finish();
        }, 1500);
    }

    @Override
    public void setError(String error) {
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void showConfirmationDialog() {
        AlertDialogFragment confirmationDialog = AlertDialogFragment
                .newInstance(R.string.accept_offer_confirmation_title, R.string.accept_offer_confirmation_detail);
        confirmationDialog.show(getSupportFragmentManager(), confirmationDialog.getClass().getSimpleName());
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int toolBarHeight = toolbar.getHeight();
        int appBarHeight = appBarLayout.getMeasuredHeight();

        int statusBarHeight = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Rect rectangle = new Rect();
            Window window = getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            statusBarHeight = rectangle.top;
        }

        // Formula from stack overflow
        float offset = ((((float) appBarHeight - toolBarHeight) + verticalOffset
                - statusBarHeight) / ((float) appBarHeight - toolBarHeight)) * 255;

        if (Math.round(offset) <= 0) {
            toolbar.setTitle(offerTitle);
            toolbar.setSubtitle(offerDates);
        } else {
            toolbar.setTitle("");
            toolbar.setSubtitle("");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        this.googleMap = googleMap;

    }

    private void addMarkerToMapAndMove(double lat, double lng) {
        LatLng marker = new LatLng(lat, lng);
        googleMap.addMarker(new MarkerOptions().position(marker)
                .title(offerTitle));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, MAP_ZOOM));
    }

    @Override
    public void onPositiveButtonClick() {
        presenter.acceptOffer();
    }

    @Override
    public void onNegativeButtonClick() { }
}
