package com.pablosanchezegido.petcity.features.registration;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pablosanchezegido.petcity.models.User;
import com.pablosanchezegido.petcity.utils.CalendarUtilsKt;

public class UserInteractorImpl implements UserInteractor {

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
}
