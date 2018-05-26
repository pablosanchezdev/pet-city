package com.pablosanchezegido.petcity.features.offers.map;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pablosanchezegido.petcity.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class OffersMapFragment extends Fragment implements OffersMapView {

    private OffersMapPresenterImpl presenter;

    public OffersMapFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers_map, container, false);
        ButterKnife.bind(this, view);
        presenter = new OffersMapPresenterImpl(this);
        return view;
    }

    @OnClick(R.id.bt_go_premium)
    public void onGoPremiumButtonClicked() {
        presenter.goPremiumButtonClicked();
    }

    @Override
    public void openLink() {
        String url = "https://play.google.com";
        Intent playStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(playStoreIntent);
    }
}
