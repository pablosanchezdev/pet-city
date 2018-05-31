package com.pablosanchezegido.petcity.features.offers.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.pablosanchezegido.petcity.models.OfferView;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.views.adapters.OffersAdapter;
import com.pablosanchezegido.petcity.views.custom.CircularProgressButton;
import com.pablosanchezegido.petcity.views.decorators.BigCardItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersListFragment extends Fragment implements OffersListView, CircularProgressButton.OnButtonClickListener {

    private static final float CARD_VIEW_HEIGHT_RATIO = 0.75f;

    @BindView(R.id.root_view) FrameLayout rootView;
    @BindView(R.id.pb) ProgressBar pb;
    @BindView(R.id.rv) RecyclerView rv;
    @BindView(R.id.bt_retry) CircularProgressButton btRetry;
    @BindView(R.id.tv_no_results) TextView tvNoResults;

    private OffersListPresenterImpl presenter;

    public OffersListFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers_list, container, false);
        ButterKnife.bind(this, view);
        initViews();
        presenter = new OffersListPresenterImpl(this, new OffersListInteractorImpl());
        presenter.fetchData();
        return view;
    }

    private void initViews() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.addItemDecoration(new BigCardItemDecoration(CARD_VIEW_HEIGHT_RATIO));

        btRetry.setOnButtonClickListener(this);
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
        rv.setAdapter(new OffersAdapter(offers, listener));
    }

    @Override
    public void setError(String error) {
        btRetry.setProgressVisible(false);
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void openItemDetail(String itemId) {
        Intent detailIntent = new Intent(getContext(), OfferDetailActivity.class);
        detailIntent.putExtra(OfferDetailActivity.OFFER_ID, itemId);
        startActivity(detailIntent);
    }

    @Override
    public void onButtonClick(View view) {
        btRetry.setProgressVisible(true);
        presenter.retryButtonClicked();
    }

    private OffersAdapter.OnItemClickListener listener = itemId -> presenter.itemRequested(itemId);
}
