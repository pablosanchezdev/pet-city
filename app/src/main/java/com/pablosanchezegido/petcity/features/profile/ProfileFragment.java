package com.pablosanchezegido.petcity.features.profile;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.models.UserView;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.views.adapters.RecentActivityAdapter;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements ProfileView {

    @BindView(R.id.root_view) ConstraintLayout rootView;
    @BindView(R.id.pb) ProgressBar pb;
    @BindView(R.id.civ_image) CircleImageView civImage;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.rv) RecyclerView rv;

    private ProfilePresenterImpl presenter;

    public ProfileFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        presenter = new ProfilePresenterImpl(this, new ProfileInteractorImpl());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        presenter.fetchData();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Hide toolbar for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        // Show toolbar when leaving this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onDetach() {
        presenter.destroy();
        super.onDetach();
    }

    @Override
    public void setLoadingVisible(boolean visible) {
        pb.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void layoutData(UserView user) {
        Picasso.get()
                .load(user.getImageUrl())
                .error(R.drawable.ic_person)
                .into(civImage);
        tvName.setText(user.getName());
        rv.setAdapter(new RecentActivityAdapter(user.getRecentActivity()));
    }

    @Override
    public void setError(String error) {
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    private void initRecyclerView() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }
}
