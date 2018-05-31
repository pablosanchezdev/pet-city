package com.pablosanchezegido.petcity.features.publish;

import com.google.firebase.firestore.GeoPoint;
import com.pablosanchezegido.petcity.features.common.OffersInteractor;
import com.pablosanchezegido.petcity.features.registration.UserInteractorImpl;
import com.pablosanchezegido.petcity.models.Offer;
import com.pablosanchezegido.petcity.models.PetType;
import com.pablosanchezegido.petcity.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PublishOfferInteractorImpl implements PublishOfferInteractor {

    @Override
    public void publishOffer(String title, String detail, String firstImage, String secondImage, String placeName,
                             double placeLatitude, double placeLongitude, long startDate, long endDate,
                             int numPets, PetType[] petTypes, double price, OnPublishOfferListener listener) {
        List<String> types = new ArrayList<>();
        for (PetType type : petTypes) {
            if (type != null) {
                types.add(type.getType());
            }
        }

        new UserInteractorImpl().fetchAuthUser(new UserInteractorImpl.OnUserFetchedListener() {
            @Override
            public void onSuccess(User user) {
                user.setRecentActivity(null);
                Offer offer = new Offer(Arrays.asList(firstImage, secondImage), title, detail, placeName, new GeoPoint(placeLatitude, placeLongitude),
                        startDate, endDate, numPets, types, price, user);
                new OffersInteractor().uploadOffer(offer, new OffersInteractor.OnOfferUploadListener() {
                    @Override
                    public void onSuccess() {
                        listener.onSuccess();
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
}
