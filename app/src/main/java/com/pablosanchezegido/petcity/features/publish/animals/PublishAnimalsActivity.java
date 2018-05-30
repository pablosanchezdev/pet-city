package com.pablosanchezegido.petcity.features.publish.animals;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.publish.PublishOfferInteractorImpl;
import com.pablosanchezegido.petcity.features.publish.SlideRightSlideBottomTransitionActivity;
import com.pablosanchezegido.petcity.models.PetType;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PublishAnimalsActivity extends SlideRightSlideBottomTransitionActivity implements PublishAnimalsView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_steps) TextView tvSteps;
    @BindView(R.id.sb_num_pets) SeekBar sbNumPets;
    @BindView(R.id.tv_num_pets) TextView tvNumPets;
    @BindView(R.id.bt_publish) Button btPublish;

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

    @OnClick(R.id.bt_publish)
    public void onPublishButtonClicked() {

    }

    @Override
    public void setNumPets(int numPets) {
        tvNumPets.setText(String.valueOf(numPets));
    }

    @Override
    public void setPublishButtonVisible(boolean visible) {
        btPublish.setVisibility(visible ? View.VISIBLE : View.GONE);
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
}
