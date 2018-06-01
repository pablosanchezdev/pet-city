package com.pablosanchezegido.petcity.features.profile;

import com.pablosanchezegido.petcity.models.User;
import com.pablosanchezegido.petcity.utils.ModelMapperKt;

public class ProfilePresenterImpl implements ProfilePresenter {

    private ProfileView view;
    private ProfileInteractor interactor;

    ProfilePresenterImpl(ProfileView view, ProfileInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void changePictureRequested() {
        view.openDialog();
    }

    @Override
    public void cameraItemClicked() {
        view.launchCamera();
    }

    @Override
    public void galleryItemClicked() {
        view.launchGallery();
    }

    @Override
    public void photoUploadRequested(String imageUri) {
        view.setLoadingVisible(true);
        interactor.uploadUserImage(imageUri, imageChangedListener);
    }

    @Override
    public void fetchData(int maxRecentActivity) {
        view.setLoadingVisible(true);
        interactor.fetchUserProfile(maxRecentActivity, listener);
    }

    @Override
    public void destroy() {
        view = null;
        interactor.detachRealtimeListener();
        interactor = null;
    }

    private ProfileInteractor.OnUserProfileFetchedListener listener =
            new ProfileInteractor.OnUserProfileFetchedListener() {
        @Override
        public void onSuccess(User user) {
            view.setLoadingVisible(false);
            view.layoutData(ModelMapperKt.userToUserView(user));
        }

        @Override
        public void onError(String error) {
            view.setLoadingVisible(false);
            view.setError(error);
        }
    };

    private ProfileInteractor.OnUserImageChangedListener imageChangedListener =
            new ProfileInteractor.OnUserImageChangedListener() {
        @Override
        public void onSuccess(String imageUrl) {
            view.changeImage(imageUrl);
            view.setLoadingVisible(false);
        }

        @Override
        public void onError(String error) {
            view.setLoadingVisible(false);
            view.setError(error);
        }
    };
}
