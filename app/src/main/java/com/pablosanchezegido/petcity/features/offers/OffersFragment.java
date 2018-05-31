package com.pablosanchezegido.petcity.features.offers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pablosanchezegido.petcity.R;
import com.pablosanchezegido.petcity.features.offers.list.OffersListFragment;
import com.pablosanchezegido.petcity.features.offers.map.OffersMapFragment;
import com.pablosanchezegido.petcity.views.adapters.TabsAdapter;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OffersFragment extends Fragment {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.view_pager) ViewPager viewPager;

    @BindString(R.string.offers_list) String listFragmentTitle;
    @BindString(R.string.offers_map) String mapFragmentTitle;

    public OffersFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        TabsAdapter adapter = new TabsAdapter(getChildFragmentManager());
        adapter.addFragment(new OffersListFragment(), listFragmentTitle);
        adapter.addFragment(new OffersMapFragment(), mapFragmentTitle);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
