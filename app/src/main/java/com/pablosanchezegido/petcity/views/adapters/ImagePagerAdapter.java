package com.pablosanchezegido.petcity.views.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.offers.detail.OfferDetailActivity;
import com.pablosanchezegido.petcity.models.Size;
import com.pablosanchezegido.petcity.utils.SizeCalculator;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    public interface OnImageClickListener {
        void onImageClick(String url);
    }

    private OnImageClickListener listener;

    private WindowManager windowManager;
    private List<String> imageUrls;

    public ImagePagerAdapter(WindowManager windowManager, List<String> imageUrls) {
        this.windowManager = windowManager;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(container.getContext())
                .inflate(R.layout.image_view_pager, container, false);

        ImageView iv = itemView.findViewById(R.id.iv);
        String url = imageUrls.get(position);
        if (url != null) {
            Size size = new SizeCalculator(windowManager, OfferDetailActivity.COLLAPSING_RELATIVE_HEIGHT).calculateSize();
            Picasso.get()
                    .load(url)
                    .error(R.drawable.ic_image_unavailable)
                    .resize(size.getWidth(), size.getHeight())
                    .onlyScaleDown()
                    .into(iv);
        }
        iv.setOnClickListener(v -> {
            if (listener != null) {
                listener.onImageClick(url);
            }
        });

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (imageUrls != null) {
            return imageUrls.size();
        } else {
            return 1;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    public void setOnImageClickListener(OnImageClickListener listener) {
        this.listener = listener;
    }
}
