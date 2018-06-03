package com.pablosanchezegido.petcity.features.offers.detail;

import java.util.List;

public interface OfferDetailView {

    void setProgressVisible(boolean visible);
    void setOfferImages(List<String> imageUrls);
    void setOfferTitle(String title);
    void setOfferDates(long startDate, long endDate);
    void setOfferDetail(String detail);
    void setUserImage(String imageUrl);
    void setUserName(String name);
    void setDogsVisible(boolean visible);
    void setCatsVisible(boolean visible);
    void setOfferNumPets(int numPets);
    void setOfferPlace(String place);
    void setOfferLocation(double lat, double lng);
    void setOfferPrice(double price);
    void setOfferAcceptedSuccessfully(String userFullName);
    void setError(String error);
    void showConfirmationDialog();
}
