package com.pablosanchezegido.petcity.views.adapters;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.models.OfferView;
import com.pablosanchezegido.petcity.utils.CalendarUtilsKt;
import com.pablosanchezegido.petcity.utils.ExtensionsKt;
import com.pablosanchezegido.petcity.utils.LocaleUtilsKt;
import com.pablosanchezegido.petcity.utils.SpannableFactoryKt;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentActivityAdapter extends RecyclerView.Adapter<RecentActivityAdapter.RecentActivityViewHolder> {

    private List<OfferView> offers;

    public RecentActivityAdapter(List<OfferView> offers) {
        this.offers = offers;
    }

    @NonNull
    @Override
    public RecentActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = ExtensionsKt.inflate(parent, R.layout.item_user_recent_activity, false);
        return new RecentActivityViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentActivityViewHolder holder, int position) {
        holder.bind(offers.get(position), position % 2 == 0);
    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    static class RecentActivityViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.root_view) LinearLayout rootView;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_price) TextView tvPrice;
        @BindView(R.id.tv_dates) TextView tvDates;

        RecentActivityViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        void bind(OfferView offer, boolean even) {
            if (even) {
                rootView.setBackgroundResource(R.color.white);
            } else {
                rootView.setBackgroundResource(R.color.almost_white);
            }

            tvTitle.setText(offer.getTitle());

            Resources res = itemView.getResources();
            String price = res.getString(R.string.offer_price, offer.getPrice());
            int endIndex = price.indexOf(LocaleUtilsKt.getLocaleDecimalSeparator());
            SpannableString spannablePrice = SpannableFactoryKt.makeRelativeSizeSpan(price, 1.4f,  0, endIndex);
            tvPrice.setText(spannablePrice);

            String startDate = CalendarUtilsKt.getDateFromTimestamp(offer.getStartDate(), null);
            String endDate = CalendarUtilsKt.getDateFromTimestamp(offer.getEndDate(), null);
            tvDates.setText(res.getString(R.string.offer_date, startDate, endDate));
        }
    }
}
