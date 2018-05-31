package com.pablosanchezegido.petcity.features.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.models.PetCityException;
import com.pablosanchezegido.petcity.models.UserView;
import com.pablosanchezegido.petcity.utils.CameraUtils;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.utils.GalleryUtils;
import com.pablosanchezegido.petcity.utils.PermissionUtilsKt;
import com.pablosanchezegido.petcity.views.adapters.RecentActivityAdapter;
import com.pablosanchezegido.petcity.views.dialogs.CameraGalleryBottomSheet;
import com.squareup.picasso.Picasso;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements ProfileView, CameraGalleryBottomSheet.OnItemClickListener {

    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 1;
    private static final int GALLERY_PERMISSIONS_REQUEST_CODE = 2;

    @BindView(R.id.root_view) ConstraintLayout rootView;
    @BindView(R.id.pb) ProgressBar pb;
    @BindView(R.id.civ_image) CircleImageView civImage;
    @BindView(R.id.tv_name) TextView tvName;
    @BindView(R.id.rv) RecyclerView rv;

    @BindString(R.string.permissions_not_granted) String permissionsNotGranted;

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSIONS_REQUEST_CODE || requestCode == GALLERY_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean somePermissionNotGranted = PermissionUtilsKt.checkGrantPermissionResults(grantResults);
                if (somePermissionNotGranted) {
                    showMessage(permissionsNotGranted);
                } else {
                    if (requestCode == CAMERA_PERMISSIONS_REQUEST_CODE) {
                        launchCameraIntent();
                    } else {
                        launchGalleryIntent();
                    }
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri imageUri;
            if (requestCode == CameraUtils.CAMERA_REQUEST_CODE) {
                imageUri = CameraUtils.CURRENT_PHOTO_URI;
            } else if (requestCode == GalleryUtils.GALLERY_REQUEST_CODE) {
                imageUri = (data != null) ? data.getData() : null;
            } else {
                imageUri = null;
            }

            presenter.photoUploadRequested(imageUri != null ? imageUri.toString() : null);
        }
    }

    @OnClick(R.id.iv_camera)
    public void onIVCameraClicked() {
        presenter.changePictureRequested();
    }

    @Override
    public void setLoadingVisible(boolean visible) {
        pb.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void openDialog() {
        CameraGalleryBottomSheet bottomSheet = new CameraGalleryBottomSheet();
        bottomSheet.show(getChildFragmentManager(), bottomSheet.getClass().getSimpleName());
        bottomSheet.setOnItemClickListener(this);
    }

    @Override
    public void launchCamera() {
        String[] permissionsToRequest = PermissionUtilsKt
                .permissionsToRequest(getContext(), new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        if (permissionsToRequest.length > 0) {
            requestPermissions(permissionsToRequest, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {
            launchCameraIntent();
        }
    }

    @Override
    public void launchGallery() {
        String[] permissionsToRequest = PermissionUtilsKt
                .permissionsToRequest(getContext(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE});
        if (permissionsToRequest.length > 0) {
            requestPermissions(permissionsToRequest, GALLERY_PERMISSIONS_REQUEST_CODE);
        } else {
            launchGalleryIntent();
        }
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
    public void changeImage(String url) {
        Picasso.get()
                .load(url)
                .error(R.drawable.ic_person)
                .into(civImage);
    }

    @Override
    public void setError(String error) {
        ExtensionsKt.makeSnackbar(rootView, error, Snackbar.LENGTH_LONG);
    }

    private void initRecyclerView() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public boolean onItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_camera:
                presenter.cameraItemClicked();
                return true;
            case R.id.action_gallery:
                presenter.galleryItemClicked();
                return true;
            default:
                return false;
        }
    }

    private void launchCameraIntent() {
        try {
            new CameraUtils(null, this).launchCamera();
        } catch (PetCityException e) {
            showMessage(e.getMessage());
        }
    }

    private void launchGalleryIntent() {
        try {
            new GalleryUtils(null, this).launchGallery();
        } catch (PetCityException e) {
            showMessage(e.getMessage());
        }
    }

    private void showMessage(String message) {
        ExtensionsKt.makeSnackbar(rootView, message, Snackbar.LENGTH_LONG);
    }
}
