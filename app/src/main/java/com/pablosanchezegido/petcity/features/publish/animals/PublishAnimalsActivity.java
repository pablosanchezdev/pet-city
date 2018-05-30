package com.pablosanchezegido.petcity.features.publish.animals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.main.MainActivity;
import com.pablosanchezegido.petcity.features.publish.PublishOfferInteractorImpl;
import com.pablosanchezegido.petcity.features.publish.SlideRightSlideBottomTransitionActivity;
import com.pablosanchezegido.petcity.features.publish.dates.PublishDatesActivity;
import com.pablosanchezegido.petcity.features.publish.images.PublishImagesActivity;
import com.pablosanchezegido.petcity.features.publish.place.PublishPlaceActivity;
import com.pablosanchezegido.petcity.features.publish.titledetail.PublishTitleDetailActivity;
import com.pablosanchezegido.petcity.models.PetType;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.views.custom.CircularProgressButton;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnTextChanged;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PublishAnimalsActivity extends SlideRightSlideBottomTransitionActivity
        implements PublishAnimalsView, CircularProgressButton.OnButtonClickListener {

    @BindView(R.id.root_view) ConstraintLayout rootView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_steps) TextView tvSteps;
    @BindView(R.id.sb_num_pets) SeekBar sbNumPets;
    @BindView(R.id.tv_num_pets) TextView tvNumPets;
    @BindView(R.id.bt_publish) CircularProgressButton btPublish;

    @BindString(R.string.publish_success) String publishSuccess;

    private PublishAnimalsPresenterImpl presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PublishAnimalsPresenterImpl(this, new PublishOfferInteractorImpl());
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_publish_animals;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        tvSteps.setText("5/5");

        sbNumPets.setOnSeekBarChangeListener(seekBarListener);

        btPublish.setOnButtonClickListener(this);
    }

    @OnCheckedChanged({R.id.cb_dogs, R.id.cb_cats})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        presenter.kindOfPetsChanged(buttonView.getId() == R.id.cb_dogs ? PetType.DOG : PetType.CAT, isChecked);
    }

    @OnTextChanged(value = R.id.ed_price, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTextChanged(Editable s) {
        String price = s.toString();
        presenter.priceChanged(Double.valueOf(price.length() > 0 ? price : "0"));
    }

    @Override
    public void setNumPets(int numPets) {
        tvNumPets.setText(String.valueOf(numPets));
    }

    @Override
    public void setPublishButtonVisible(boolean visible) {
        btPublish.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setPublishButtonProgressVisible(boolean visible) {
        btPublish.setProgressVisible(visible);
    }

    @Override
    public void setPublishButtonEnabled(boolean enabled) {
        btPublish.setButtonEnabled(enabled);
    }

    @Override
    public void onPublishSuccess() {
        Function1<View, Unit> listener = view -> {
            // Return to main activity
            Intent mainActivityIntent = new Intent(PublishAnimalsActivity.this, MainActivity.class);
            mainActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(mainActivityIntent);
            return Unit.INSTANCE;
        };
        ExtensionsKt.makeSnackbarWithAction(rootView, publishSuccess, Snackbar.LENGTH_INDEFINITE, R.string.view, listener);
    }

    @Override
    public void onPublishError(String error) {
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    private SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            presenter.numPetsChanged(progress);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) { }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) { }
    };

    @Override
    public void onButtonClick(View view) {
        Intent previousIntent = getIntent();
        String title = previousIntent.getStringExtra(PublishTitleDetailActivity.TITLE);
        String detail = previousIntent.getStringExtra(PublishTitleDetailActivity.DETAIL);
        String firstImage = previousIntent.getStringExtra(PublishImagesActivity.FIRST_IMAGE_URI);
        String secondImage = previousIntent.getStringExtra(PublishImagesActivity.SECOND_IMAGE_URI);
        String placeName = previousIntent.getStringExtra(PublishPlaceActivity.PLACE_NAME);
        double placeLat = previousIntent.getDoubleExtra(PublishPlaceActivity.PLACE_LAT, 0.0);
        double placeLng = previousIntent.getDoubleExtra(PublishPlaceActivity.PLACE_LNG, 0.0);
        long startDate = previousIntent.getLongExtra(PublishDatesActivity.START_DATE, 0);
        long endDate = previousIntent.getLongExtra(PublishDatesActivity.END_DATE, 0);
        presenter.publishOfferRequested(title, detail, firstImage, secondImage, placeName, placeLat,
                placeLng, startDate, endDate);
    }
}
