package com.pablosanchezegido.petcity.features.registration;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.pablosanchezegido.petcity.features.login.AuthInteractorImpl;
import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserInteractorImpl implements UserInteractor {

    private static final String USERS_REF = "users";
    private static final String USERS_IMAGE_REF = "photoUrl";
    private static final String USER_OFFERS_ACCEPTED_REF = "offers-accepted";

    private CollectionReference usersRef;
    private ListenerRegistration listenerRegistration;

    public UserInteractorImpl() {
        usersRef = FirebaseFirestore.getInstance().collection(USERS_REF);
    }

    @Override
    public void createUser(String id, String email, String name, String phoneNumber, OnUserCreatedListener listener) {
        User user = new User("", email, name, phoneNumber, null);
        usersRef.document(id).set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
                    } else {
                        listener.onError((task.getException() != null) ? task.getException().getMessage() : "Unknown error");
                    }
                });
    }

    @Override
    public void fetchAuthUser(OnUserFetchedListener listener) {
        String userId = AuthInteractorImpl.getUserId();
        if (userId != null) {
            usersRef.document(userId).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            User user = task.getResult().toObject(User.class);
                            listener.onSuccess(user);
                        } else {
                            listener.onError(task.getException().getMessage());
                        }
                    });
        }
    }

    @Override
    public void fetchUserProfile(int maxRecentActivity, OnUserFetchedListener listener) {
        fetchAuthUser(new OnUserFetchedListener() {
            @Override
            public void onSuccess(User user) {
                fetchUserRecentActivity(maxRecentActivity, new OnUserOffersFetchedListener() {
                    @Override
                    public void onSuccess(List<Offer> offers) {
                        user.setRecentActivity(offers);
                        listener.onSuccess(user);
                    }

                    @Override
                    public void onError(String error) {
                        listener.onError(error);
                    }
                });
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }

    @Override
    public void changeUserProfileImage(String url) {
        String userId = AuthInteractorImpl.getUserId();
        if (userId != null) {
            Map<String, Object> data = new HashMap<>();
            data.put(USERS_IMAGE_REF, url);
            usersRef.document(userId).set(data, SetOptions.merge());
        }
    }

    @Override
    public void insertOfferAccepted(Offer offer, OnOfferAcceptedInsertedListener listener) {
        String userId = AuthInteractorImpl.getUserId();
        if (userId != null) {
            usersRef.document(userId)
                    .collection(USER_OFFERS_ACCEPTED_REF)
                    .document(offer.getId())
                    .set(offer)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            listener.onSuccess();
                        } else {
                            listener.onError(task.getException().getMessage());
                        }
                    });
        }
    }

    @Override
    public void detachUserProfileRealtimeListener() {
        listenerRegistration.remove();
    }

    private void fetchUserRecentActivity(int maxRecentActivity, OnUserOffersFetchedListener listener) {
        String userId = AuthInteractorImpl.getUserId();
        if (userId != null) {
            Query query = usersRef.document(userId)
                    .collection(USER_OFFERS_ACCEPTED_REF)
                    .limit(maxRecentActivity);
            listenerRegistration = query
                    .addSnapshotListener((queryDocumentSnapshots, e) -> {
                        if (e != null) {
                            listener.onError(e.getLocalizedMessage());
                            return;
                        }

                        List<Offer> offers = new ArrayList<>();
                        if (queryDocumentSnapshots != null) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                if (doc != null) {
                                    offers.add(doc.toObject(Offer.class));
                                }
                            }

                            listener.onSuccess(offers);
                        } else {
                            // Send empty array list when the user does not have recent offers accepted
                            listener.onSuccess(new ArrayList<>());
                        }
                    });
        }
    }
}
