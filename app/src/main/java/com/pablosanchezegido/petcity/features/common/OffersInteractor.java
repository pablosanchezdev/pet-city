package com.pablosanchezegido.petcity.features.common;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.pablosanchezegido.petcity.features.registration.UserInteractor;
import com.pablosanchezegido.petcity.features.registration.UserInteractorImpl;
import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.utils.BoundaryLatLng;

import java.util.ArrayList;
import java.util.List;

public class OffersInteractor {

    public interface OnOffersFetched {
        void onSuccess(List<Offer> offers);
        void onError(String error);
    }

    public interface OnOfferUploadListener {
        void onSuccess();
        void onError(String error);
    }

    public interface OnOfferFetchedListener {
        void onSuccess(Offer offer);
        void onError(String error);
    }

    public interface OnOfferAcceptedListener {
        void onSuccess();
        void onError(String error);
    }

    private static final String OFFERS_PATH = "offers";
    private static final String LOCATION_PATH = "location";

    private CollectionReference offersRef;

    public OffersInteractor() {
        this.offersRef = FirebaseFirestore.getInstance().collection(OFFERS_PATH);
    }

    public void fetchOffersWithinBounds(BoundaryLatLng boundary, OnOffersFetched listener) {
        LatLng lowerBound = boundary.getMinLatLng();
        LatLng upperBound = boundary.getMaxLatLng();

        offersRef
                .whereGreaterThanOrEqualTo(LOCATION_PATH, new GeoPoint(lowerBound.latitude, lowerBound.longitude))
                .whereLessThanOrEqualTo(LOCATION_PATH, new GeoPoint(upperBound.latitude, upperBound.longitude))
                .orderBy(LOCATION_PATH, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Offer> result = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Offer offer = document.toObject(Offer.class);
                            offer.setId(document.getId());
                            result.add(offer);
                        }
                        listener.onSuccess(result);
                    } else {
                        listener.onError(task.getException().getMessage());
                    }
                });
    }

    public void uploadOffer(Offer offer, OnOfferUploadListener listener) {
        String offerId = offersRef.document().getId();
        new ImagesInteractor().uploadImages(new String[] {offerId, offerId}, offer.getImages(),
                new ImagesInteractor.OnImagesUploadListener() {
            @Override
            public void onSuccess(List<String> imageUrls) {
                // Images have been successfully uploaded
                offer.setImages(imageUrls); // Change image uris by image urls
                offersRef.document(offerId)
                        .set(offer)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                listener.onSuccess();
                            } else {
                                listener.onError(task.getException().getMessage());
                            }
                        });
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }

    public void fetchOffer(String offerId, OnOfferFetchedListener listener) {
        offersRef.document(offerId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Offer offer = task.getResult().toObject(Offer.class);
                        offer.setId(offerId);
                        listener.onSuccess(offer);
                    } else {
                        listener.onError(task.getException().getMessage());
                    }
                });
    }

    public void acceptOffer(Offer offer, OnOfferAcceptedListener listener) {
        new UserInteractorImpl().insertOfferAccepted(offer, new UserInteractor.OnOfferAcceptedInsertedListener() {
            @Override
            public void onSuccess() {
                offersRef.document(offer.getId()).delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                listener.onSuccess();
                            } else {
                                listener.onError(task.getException().getMessage());
                            }
                        });
            }

            @Override
            public void onError(String error) {
                listener.onError(error);
            }
        });
    }
}
