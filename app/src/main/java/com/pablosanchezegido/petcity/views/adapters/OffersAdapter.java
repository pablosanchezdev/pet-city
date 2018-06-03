package com.pablosanchezegido.petcity.views.adapters;

import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.offers.list.OffersListFragment;
import com.pablosanchezegido.petcity.models.OfferView;
import com.pablosanchezegido.petcity.models.Size;
import com.pablosanchezegido.petcity.utils.CalendarUtilsKt;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.utils.LocaleUtilsKt;
import com.pablosanchezegido.petcity.utils.ModelMapperKt;
import com.pablosanchezegido.petcity.utils.SizeCalculator;
import com.pablosanchezegido.petcity.utils.SpannableFactoryKt;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    private List<OfferView> offers;
    private WindowManager windowManager;
    private OnItemClickListener listener;

    public OffersAdapter(List<OfferView> offers, WindowManager windowManager, OnItemClickListener listener) {
        this.offers = offers;
        this.windowManager = windowManager;
        this.listener = listener;
    }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = ExtensionsKt.inflate(parent, R.layout.item_offer, false);
        return new OffersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {
        holder.bindOffer(offers.get(position), windowManager, listener);
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

        void bindOffer(OfferView offer, WindowManager windowManager, final OnItemClickListener listener) {
            SizeCalculator sizeCalculator = new SizeCalculator(windowManager, OffersListFragment.CARD_VIEW_HEIGHT_RATIO);
            Size size = sizeCalculator.calculateSize();
            Picasso.get()
                    .load(offer.getImage())
                    .error(R.drawable.ic_image_unavailable)
                    .resize(size.getWidth(), size.getHeight())
                    .onlyScaleDown()
                    .into(ivOfferImage);

            tvTitle.setText(offer.getTitle());

            Resources res = itemView.getResources();
            String startDate = CalendarUtilsKt.getDateFromTimestamp(offer.getStartDate(), null);
            String endDate = CalendarUtilsKt.getDateFromTimestamp(offer.getEndDate(), null);
            tvDate.setText(res.getString(R.string.offer_date, startDate, endDate));
            tintDrawable(tvDate.getCompoundDrawablesRelative()[0]);

            if (offer.getDistance() == ModelMapperKt.DISTANCE_NOT_AVAILABLE) {
                tvDistance.setText(R.string.radius_not_available);
            } else {
                String distance = res.getString(R.string.offer_distance, offer.getDistance());
                tvDistance.setText(distance);
            }
            tintDrawable(tvDistance.getCompoundDrawablesRelative()[0]);

            String price = res.getString(R.string.offer_price, offer.getPrice());
            int endIndex = price.indexOf(LocaleUtilsKt.getLocaleDecimalSeparator());
            SpannableString spannablePrice = SpannableFactoryKt.makeRelativeSizeSpan(price, 1.4f,  0, endIndex);
            tvPrice.setText(spannablePrice);

            itemView.setOnClickListener(v -> listener.onItemClick(offer.getId()));
        }

        private void tintDrawable(Drawable drawable) {
            if (drawable != null) {
                drawable.setColorFilter(ContextCompat.getColor(itemView.getContext(), R.color.dark_gray), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }
}
