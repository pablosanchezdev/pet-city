package com.pablosanchezegido.petcity.views.adapters;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pablosanchezegido.petcity.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagePagerAdapter extends PagerAdapter {

    public interface OnImageClickListener {
        void onImageClick(String url);
    }

    private OnImageClickListener listener;

    private List<String> imageUrls;

    public ImagePagerAdapter(List<String> imageUrls) {
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
            Picasso.get().load(url).error(R.drawable.ic_image_unavailable).into(iv);
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
