package com.pablosanchezegido.petcity.views.adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.models.OfferView;
import com.pablosanchezegido.petcity.utils.CalendarUtilsKt;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.utils.LocaleUtilsKt;
import com.pablosanchezegido.petcity.utils.SpannableFactoryKt;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

    private List<OfferView> offers;

    public OffersAdapter(List<OfferView> offers) {
        this.offers = offers;
    }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = ExtensionsKt.inflate(parent, R.layout.item_offer, false);
        return new OffersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {
        holder.bindOffer(offers.get(position));
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    static class OffersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_offer_image) ImageView ivOfferImage;
        @BindView(R.id.tv_offer_title) TextView tvTitle;
        @BindView(R.id.tv_offer_date) TextView tvDate;
        @BindView(R.id.tv_offer_distance) TextView tvDistance;
        @BindView(R.id.tv_offer_price) TextView tvPrice;

        OffersViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bindOffer(OfferView offer) {
            Picasso.get()
                    .load(offer.getImage())
                    .into(ivOfferImage);

            tvTitle.setText(offer.getTitle());

            Resources res = itemView.getResources();
            String startDate = CalendarUtilsKt.getDateFromTimestamp(offer.getStartDate(), null);
            String endDate = CalendarUtilsKt.getDateFromTimestamp(offer.getEndDate(), null);
            tvDate.setText(res.getString(R.string.offer_date, startDate, endDate));

            String distance = res.getString(R.string.offer_distance, offer.getDistance());
            tvDistance.setText(distance);

            String price = res.getString(R.string.offer_price, offer.getPrice());
            int endIndex = price.indexOf(LocaleUtilsKt.getLocaleDecimalSeparator());
            SpannableString spannablePrice = SpannableFactoryKt.makeRelativeSizeSpan(price, 1.4f,  0, endIndex);
            tvPrice.setText(spannablePrice);
        }
    }
}
