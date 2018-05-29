package com.pablosanchezegido.petcity.features.publish.dates;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.publish.SlideRightSlideBottomTransitionActivity;
import com.pablosanchezegido.petcity.utils.CalendarUtilsKt;
import com.pablosanchezegido.petcity.views.dialogs.DatePickerFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class PublishDatesActivity extends SlideRightSlideBottomTransitionActivity
        implements PublishDatesView, DatePickerFragment.DateSetListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_steps) TextView tvSteps;
    @BindView(R.id.ed_start_date) EditText edStartDate;
    @BindView(R.id.ed_end_date) EditText edEndDate;
    @BindView(R.id.tv_error) TextView tvError;
    @BindView(R.id.bt_next) Button btNext;

    private PublishDatesPresenterImpl presenter;
    private int editTextClicked;
    private long firstDateTimestamp, secondDateTimestamp;
    private boolean firstDateSet, secondDateSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PublishDatesPresenterImpl(this);
        firstDateTimestamp = secondDateTimestamp = 0;
        firstDateSet = secondDateSet = false;
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_publish_dates;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        tvSteps.setText("4/5");
    }

    @OnClick({R.id.ed_start_date, R.id.ed_end_date})
    public void onEditTextClicked(View view) {
        editTextClicked = view.getId();
        presenter.dateInputRequested();
    }

    @OnClick(R.id.bt_next)
    public void onNextButtonClicked() {
        presenter.nextPageRequested();
    }

    @Override
    public void showDateDialog() {
        int year = CalendarUtilsKt.getCurrentYear();
        int month = CalendarUtilsKt.getCurrentMonth();
        int day = CalendarUtilsKt.getCurrentDay();

        DatePickerFragment datePickerDialog = DatePickerFragment
                .newInstance(year, month, day, CalendarUtilsKt.getDateTimestamp(year, month, day), DatePickerFragment.DATE_BOUNDS_NOT_SET);
        datePickerDialog.show(getSupportFragmentManager(), datePickerDialog.getClass().getSimpleName());
    }

    @Override
    public void setErrorVisible(boolean visible) {
        tvError.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setNextButtonVisible(boolean visible) {
        btNext.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void requestNextPage() {

    }

    @Override
    public void onDateSet(int year, int month, int day) {
        String date = CalendarUtilsKt.getDateFormatted(year, month, day);
        long timestamp = CalendarUtilsKt.getDateTimestamp(year, month, day);
        if (editTextClicked == R.id.ed_start_date) {
            edStartDate.setText(date);
            firstDateTimestamp = timestamp;
            firstDateSet = true;
        } else {
            edEndDate.setText(date);
            secondDateTimestamp = timestamp;
            secondDateSet = true;
        }

        presenter.validateDates(firstDateTimestamp, firstDateSet, secondDateTimestamp, secondDateSet);
    }
}
