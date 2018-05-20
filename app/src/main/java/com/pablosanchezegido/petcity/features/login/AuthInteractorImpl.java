package com.pablosanchezegido.petcity.features.login;

import com.google.firebase.auth.FirebaseAuth;

public class AuthInteractorImpl implements AuthInteractor {

    private final FirebaseAuth auth;

    public AuthInteractorImpl() {
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean isUserLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    @Override
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

    @Override
    public void registerUser(String email, String password, String fullName, String phoneNumber, String birthDate, OnAuthFinishedListener listener) {
        auth.createUserWithEmailAndPassword(email, password)
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
}
