package com.pablosanchezegido.petcity.features.common;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthInteractor {

    public interface OnAuthFinishedListener {
        void onSuccess();
        void onError(String error);
    }

    private final FirebaseAuth auth;
    private UserInteractor userInteractor;

    public AuthInteractor() {
        this.auth = FirebaseAuth.getInstance();
        this.userInteractor = new UserInteractor();
    }

    public boolean isUserLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public void loginUser(String email, String password, OnAuthFinishedListener listener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onSuccess();
                    } else {
                        if (task.getException() != null) {
                            listener.onError(task.getException().getMessage());
                        } else {
                            listener.onError("Unknown error");
                        }
                    }
                });
    }

    public void registerUser(String email, String password, String fullName, String phoneNumber, OnAuthFinishedListener listener) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        userInteractor.createUser(getCurrentUser().getUid(), email, fullName, phoneNumber,
                                new UserInteractor.OnUserCreatedListener() {
                            @Override
                            public void onSuccess() {
                                listener.onSuccess();
                            }

                            @Override
                            public void onError(String error) {
                                listener.onError(error);
                            }
                        });
                    } else {
                        if (task.getException() != null) {
                            listener.onError(task.getException().getMessage());
                        } else {
                            listener.onError("Unknown error");
                        }
                    }
                });
    }

    public void logoutUser() {
        auth.signOut();
    }

    public static String getUserId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return (user != null) ? user.getUid() : null;
    }

    private FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }
}
