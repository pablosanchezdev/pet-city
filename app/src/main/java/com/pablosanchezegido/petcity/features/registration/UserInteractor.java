package com.pablosanchezegido.petcity.features.registration;

import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.models.User;

import java.util.List;

public interface UserInteractor {

    interface OnUserCreatedListener {
        void onSuccess();
        void onError(String error);
    }

    interface OnUserFetchedListener {
        void onSuccess(User user);
        void onError(String error);
    }

    interface OnUserOffersFetchedListener {
        void onSuccess(List<Offer> offers);
        void onError(String error);
    }

    interface OnOfferAcceptedInsertedListener {
        void onSuccess();
        void onError(String error);
    }

    void createUser(String id, String email, String name, String phoneNumber, String birthDate, OnUserCreatedListener listener);
    void fetchAuthUser(OnUserFetchedListener listener);
    void fetchUserProfile(OnUserFetchedListener listener);
    void changeUserProfileImage(String url);
    void insertOfferAccepted(Offer offer, OnOfferAcceptedInsertedListener listener);
    void detachUserProfileRealtimeListener();
}
