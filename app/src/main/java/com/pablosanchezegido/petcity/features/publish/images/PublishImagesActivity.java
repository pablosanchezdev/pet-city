package com.pablosanchezegido.petcity.features.publish.images;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.publish.SlideRightSlideBottomTransitionActivity;
import com.pablosanchezegido.petcity.features.publish.place.PublishPlaceActivity;
import com.pablosanchezegido.petcity.features.publish.titledetail.PublishTitleDetailActivity;
import com.pablosanchezegido.petcity.models.PetCityException;
import com.pablosanchezegido.petcity.utils.CameraUtils;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.utils.GalleryUtils;
import com.pablosanchezegido.petcity.utils.PermissionUtilsKt;
import com.pablosanchezegido.petcity.views.dialogs.CameraGalleryBottomSheet;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

public class PublishImagesActivity extends SlideRightSlideBottomTransitionActivity
        implements PublishImagesView, CameraGalleryBottomSheet.OnItemClickListener {

    public static final String FIRST_IMAGE_URI_KEY = "firstImageUri";
    public static final String SECOND_IMAGE_URI_KEY = "secondImageUri";
    private static final String BUTTON_NEXT_VISIBILITY_KEY  = "buttonNextVisibility";
    private static final int CAMERA_PERMISSIONS_REQUEST_CODE = 1;
    private static final int GALLERY_PERMISSIONS_REQUEST_CODE = 2;

    @BindView(R.id.root_view) ConstraintLayout rootView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.tv_steps) TextView tvSteps;
    @BindView(R.id.iv_image_first) ImageView ivImageFirst;
    @BindView(R.id.iv_image_second) ImageView ivImageSecond;
    @BindView(R.id.bt_next) Button btNext;

    @BindString(R.string.permissions_not_granted) String permissionsNotGranted;

    private PublishImagesPresenterImpl presenter;

    private Uri firstImageUri, secondImageUri;
    private int buttonPressed;
    private boolean firstImageSet, secondImageSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new PublishImagesPresenterImpl(this);
        firstImageSet = secondImageSet = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save activity state
        outState.putParcelable(FIRST_IMAGE_URI_KEY, firstImageUri);
        outState.putParcelable(SECOND_IMAGE_URI_KEY, secondImageUri);
        outState.putInt(BUTTON_NEXT_VISIBILITY_KEY, btNext.getVisibility());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore activity state
        firstImageUri = savedInstanceState.getParcelable(FIRST_IMAGE_URI_KEY);
        secondImageUri = savedInstanceState.getParcelable(SECOND_IMAGE_URI_KEY);
        int btNextVisibility = savedInstanceState.getInt(BUTTON_NEXT_VISIBILITY_KEY);

        if (firstImageUri != null) {
            ivImageFirst.setImageURI(firstImageUri);
        }
        if (secondImageUri != null) {
            ivImageSecond.setImageURI(secondImageUri);
        }

        btNext.setVisibility(btNextVisibility);
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        super.onDestroy();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_publish_images;
    }

    @Override
    protected void initViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        tvSteps.setText("2/5");
    }

    @OnClick({R.id.bt_image_first, R.id.bt_image_second})
    public void onImageButtonClicked(View view) {
        buttonPressed = view.getId();
        presenter.imageButtonClicked();
    }

    @OnClick(R.id.bt_next)
    public void onNextButtonClicked(View view) {
        presenter.nextButtonClicked();
    }

    @Override
    public void openDialog() {
        CameraGalleryBottomSheet bottomSheet = new CameraGalleryBottomSheet();
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getClass().getSimpleName());
    }

    @Override
    public void launchCamera() {
        String[] permissionsToRequest = PermissionUtilsKt
                .permissionsToRequest(this, new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE});
        if (permissionsToRequest.length > 0) {
            ActivityCompat.requestPermissions(this, permissionsToRequest, CAMERA_PERMISSIONS_REQUEST_CODE);
        } else {
            launchCameraIntent();
        }
    }

    @Override
    public void launchGallery() {
        String[] permissionsToRequest = PermissionUtilsKt
                .permissionsToRequest(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE});
        if (permissionsToRequest.length > 0) {
            ActivityCompat.requestPermissions(this, permissionsToRequest, GALLERY_PERMISSIONS_REQUEST_CODE);
        } else {
            launchGalleryIntent();
        }
    }

    @Override
    public void setNextButtonVisible(boolean visible) {
        btNext.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void requestNextPage() {
        Intent nextActivityIntent = new Intent(this, PublishPlaceActivity.class);
        Intent previousIntent = getIntent();
        nextActivityIntent.putExtra(PublishTitleDetailActivity.TITLE_KEY, previousIntent.getStringExtra(PublishTitleDetailActivity.TITLE_KEY));
        nextActivityIntent.putExtra(PublishTitleDetailActivity.DETAIL_KEY, previousIntent.getStringExtra(PublishTitleDetailActivity.DETAIL_KEY));
        nextActivityIntent.putExtra(FIRST_IMAGE_URI_KEY, firstImageUri);
        nextActivityIntent.putExtra(SECOND_IMAGE_URI_KEY, secondImageUri);
        launchNextActivity(nextActivityIntent);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri currentUri;
            if (requestCode == CameraUtils.CAMERA_REQUEST_CODE) {
                currentUri = CameraUtils.CURRENT_PHOTO_URI;
            } else if (requestCode == GalleryUtils.GALLERY_REQUEST_CODE) {
                currentUri = (data != null) ? data.getData() : null;
            } else {
                currentUri = null;
            }

            if (buttonPressed == R.id.bt_image_first) {
                ivImageFirst.setImageURI(currentUri);
                firstImageUri = currentUri;
                firstImageSet = true;
            } else if (buttonPressed == R.id.bt_image_second) {
                ivImageSecond.setImageURI(currentUri);
                secondImageUri = currentUri;
                secondImageSet = true;
            }

            presenter.checkBothImages(firstImageSet, secondImageSet);
        }
    }

    private void launchCameraIntent() {
        try {
            new CameraUtils(this).launchCamera();
        } catch (PetCityException e) {
            showMessage(e.getMessage());
        }
    }

    private void launchGalleryIntent() {
        try {
            new GalleryUtils(this).launchGallery();
        } catch (PetCityException e) {
            showMessage(e.getMessage());
        }
    }

    private void showMessage(String message) {
        ExtensionsKt.makeSnackbar(rootView, message, Snackbar.LENGTH_LONG);
    }
}
