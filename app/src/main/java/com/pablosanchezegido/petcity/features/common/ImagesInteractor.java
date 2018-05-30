package com.pablosanchezegido.petcity.features.common;

import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ImagesInteractor {

    public interface OnImagesUploadListener {
        void onSuccess(List<String> imageUrls);
        void onError(String error);
    }

    public interface OnImageUploadListener {
        void onSuccess(String imageUrl);
        void onError(String error);
    }

    private static final String POST_IMAGES_PATH = "posts";

    private StorageReference rootRef;
    private AtomicInteger counter;  // Counter to know when both images are uploaded
    private List<String> imageUrls;

    public ImagesInteractor() {
        rootRef = FirebaseStorage.getInstance().getReference();
        counter = new AtomicInteger();
        imageUrls = new ArrayList<>();
    }

    public void uploadImages(String[] ids, List<String> uris, OnImagesUploadListener listener) {
        if (ids.length != uris.size()) {
            throw new IllegalArgumentException("Ids and URIs must have same length");
        }

        counter.set(0);
        for (int i = 0; i < ids.length; i++) {
            uploadImage(ids[i], i+1, uris.get(i), new OnImageUploadListener() {
                @Override
                public void onSuccess(String imageUrl) {
                    counter.incrementAndGet();
                    imageUrls.add(imageUrl);
                    if (counter.intValue() == ids.length) {  // Upload finished
                        listener.onSuccess(imageUrls);
                    }
                }

                @Override
                public void onError(String error) {
                    listener.onError(error);
                }
            });
        }
    }

    public void uploadImage(String id, int number, String uri, OnImageUploadListener listener) {
        final String path = String.format("%s/%s", POST_IMAGES_PATH, id);
        final StorageReference imageRef = rootRef.child(path).child("image" + number + ".jpg");
        imageRef.putFile(Uri.parse(uri))
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        listener.onError(task.getException().getMessage());
                        return;
                    }

                    listener.onSuccess(task.getResult().getDownloadUrl().toString());
                });
    }
}
