package com.pablosanchezegido.petcity.features.profile;

import com.pablosanchezegido.petcity.features.common.ImagesInteractor;
import com.pablosanchezegido.petcity.features.common.AuthInteractor;
import com.pablosanchezegido.petcity.features.common.UserInteractor;
import com.pablosanchezegido.petcity.models.User;

public class ProfileInteractorImpl implements ProfileInteractor {

    private UserInteractor userInteractor;

    ProfileInteractorImpl() {
        userInteractor = new UserInteractor();
    }

    @Override
    public void fetchUserProfile(int maxRecentActivity, OnUserProfileFetchedListener listener) {
        userInteractor.fetchUserProfile(maxRecentActivity, new UserInteractor.OnUserFetchedListener() {
            @Override
            public void onSuccess(User user) {
                listener.onSuccess(user);
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }

    @Override
    public void uploadUserImage(String imageUri, OnUserImageChangedListener listener) {
        String userId = AuthInteractor.getUserId();
        new ImagesInteractor().uploadImage(userId, 1, imageUri, ImagesInteractor.ImageTypes.USERS,
                new ImagesInteractor.OnImageUploadListener() {
            @Override
            public void onSuccess(String imageUrl) {
                userInteractor.changeUserProfileImage(imageUrl);
                listener.onSuccess(imageUrl);
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }

    @Override
    public void detachRealtimeListener() {
        userInteractor.detachUserProfileRealtimeListener();
    }
}
