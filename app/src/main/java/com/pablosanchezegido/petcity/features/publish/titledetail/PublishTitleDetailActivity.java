package com.pablosanchezegido.petcity.features.publish.titledetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.publish.SlideRightSlideBottomTransitionActivity;
import com.pablosanchezegido.petcity.features.publish.images.PublishImagesActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class PublishTitleDetailActivity extends SlideRightSlideBottomTransitionActivity implements PublishTitleDetailView {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_steps) TextView tvSteps;
    @BindView(R.id.ed_offer_title) TextInputEditText edOfferTitle;
    @BindView(R.id.ed_offer_detail) TextInputEditText edOfferDetail;
    @BindView(R.id.bt_next) Button btNext;

    private PublishTitleDetailPresenterImpl presenter;

    private String offerTitle, offerDetail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PublishTitleDetailPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_publish_title_detail;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        tvSteps.setText("1/5");

        // Edittexts are multiline, so explicitly set ime actions
        edOfferTitle.setImeOptions(EditorInfo.IME_ACTION_GO);
        edOfferTitle.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        edOfferDetail.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edOfferDetail.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
    }

    @OnTextChanged(value = {R.id.ed_offer_title}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onTitleTextChanged(CharSequence text) {
        offerTitle = text.toString();
        presenter.titleTextChanged(offerTitle);
    }

    @OnTextChanged(value = {R.id.ed_offer_detail}, callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onDetailTextChanged(CharSequence text) {
        offerDetail = text.toString();
        presenter.detailTextChanged(offerDetail);
    }

    @OnClick(R.id.bt_next)
    public void onNextButtonClicked() {
        presenter.nextButtonClicked();
    }

    @Override
    public void setNextButtonVisible(boolean visible) {
        btNext.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void requestNextPage() {
        Intent nextActivityIntent = new Intent(this, PublishImagesActivity.class);
        nextActivityIntent.putExtra(PublishImagesActivity.OFFER_TITLE_KEY, offerTitle);
        nextActivityIntent.putExtra(PublishImagesActivity.OFFER_DETAIL_KEY, offerDetail);
        launchNextActivity(nextActivityIntent);
    }
}
