package com.pablosanchezegido.petcity.features.offers.list;

import com.pablosanchezegido.petcity.models.OfferView;

import java.util.List;

public interface OffersListView {

    void setProgressVisible(boolean visible);
    void setRetryButtonVisible(boolean visible);
    void setNoResultsVisible(boolean visible);
    void layoutData(List<OfferView> offers);
    void setError(String error);
}
