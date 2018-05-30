package com.pablosanchezegido.petcity.features.registration;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pablosanchezegido.petcity.features.login.AuthInteractorImpl;
import com.pablosanchezegido.petcity.models.User;
import com.pablosanchezegido.petcity.utils.CalendarUtilsKt;

public class UserInteractorImpl implements UserInteractor {

    public interface OnUserFetchedListener {
        void onSuccess(User user);
        void onError(String error);
    }

    private static final String USERS_REF = "users";

    private CollectionReference usersRef;

    public UserInteractorImpl() {
        usersRef = FirebaseFirestore.getInstance().collection(USERS_REF);
    }

    @Override
    public void createUser(String id, String email, String name, String phoneNumber, String birthDate, OnUserCreatedListener listener) {
        User user = new User(null, "photoUrl", email, name, phoneNumber, CalendarUtilsKt.getDateTimestamp(birthDate));
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
        String userId = new AuthInteractorImpl().getUserId();
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
