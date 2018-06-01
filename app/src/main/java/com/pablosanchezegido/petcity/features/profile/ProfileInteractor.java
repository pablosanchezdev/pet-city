package com.pablosanchezegido.petcity.features.profile;

import com.pablosanchezegido.petcity.models.User;

public interface ProfileInteractor {

    interface OnUserProfileFetchedListener {
        void onSuccess(User user);
        void onError(String error);
    }

    interface OnUserImageChangedListener {
        void onSuccess(String imageUrl);
        void onError(String error);
    }

    void fetchUserProfile(int maxRecentActivity, OnUserProfileFetchedListener listener);
    void uploadUserImage(String imageUri, OnUserImageChangedListener listener);
    void detachRealtimeListener();
}
