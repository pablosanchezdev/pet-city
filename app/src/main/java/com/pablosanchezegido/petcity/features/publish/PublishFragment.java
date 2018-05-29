package com.pablosanchezegido.petcity.features.publish;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.publish.titledetail.PublishTitleDetailActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PublishFragment extends Fragment implements PublishView {

    private PublishPresenterImpl presenter;

    public PublishFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_publish, container, false);
        ButterKnife.bind(this, view);
        presenter = new PublishPresenterImpl(this);
        return view;
    }

    @Override
    public void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @OnClick(R.id.bt_publish_offer)
    public void onPublishOfferClicked() {
        presenter.publishOfferClicked();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void startPublishProcess() {
        Intent publishIntent = new Intent(getContext(), PublishTitleDetailActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
        ActivityCompat.startActivity(getContext(), publishIntent, options.toBundle());
    }
}
