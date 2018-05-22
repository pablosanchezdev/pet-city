package com.pablosanchezegido.petcity.features.common;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.utils.BoundaryLatLng;

import java.util.ArrayList;
import java.util.List;

public class OffersInteractor {

    public interface OnOffersFetched {
        void onSuccess(List<Offer> offers);
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
                            result.add(document.toObject(Offer.class));
                        }
                        listener.onSuccess(result);
                    } else {
                        listener.onError(task.getException().getMessage());
                    }
                });
    }
}
