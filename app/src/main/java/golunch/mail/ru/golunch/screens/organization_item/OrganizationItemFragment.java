package golunch.mail.ru.golunch.screens.organization_item;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import golunch.mail.ru.golunch.MainActivity;
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.helper.BadgeHelper;
import golunch.mail.ru.golunch.screens.base.SingleActivity;
import golunch.mail.ru.golunch.screens.organization_item.pager.additional_info.AdditionalInfoFragment;
import golunch.mail.ru.golunch.screens.organization_item.pager.feedback.FeedbackFragment;
import golunch.mail.ru.golunch.screens.organization_item.pager.menu.MenuFragment;

import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.FIREBASE_DB_URL;
import static golunch.mail.ru.golunch.screens.organization_item.pager.BaseOrganizationFragment.ORGANIZATION_NAME;

public class OrganizationItemFragment extends Fragment {

    private static final int PAGE_COUNT = 3;

    private final static String BANNER_NAME = "BANNER_NAME";

    ViewPagerInScrollView pager;
    NestedScrollView scrollView;
    TabLayout pagerTabLayout;
    PagerAdapter pagerAdapter;
    private ImageView banner;
    private String bannerName;
    private OrganizationItemActivity activity;
    float toolbarTransparency = 0;

    String organizationName;

    public static OrganizationItemFragment newInstance(String organizationName, String bannerName) {

        OrganizationItemFragment organizationItemFragment = new OrganizationItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ORGANIZATION_NAME, organizationName);
        arguments.putString(BANNER_NAME, bannerName);
        organizationItemFragment.setArguments(arguments);
        return organizationItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        organizationName = getArguments().getString(ORGANIZATION_NAME);
        bannerName = getArguments().getString(BANNER_NAME);
        activity = ((OrganizationItemActivity) getActivity());
        // Ask Sergey for additional information about next aruments
        toolbarTransparency = OrganizationItemActivity.convertDpToPixel(250 - 24 - 56, getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.toolbar_activity_organizationitem);
        toolbar.setTitle(organizationName);
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.org_item, null);
        banner = (ImageView) view.findViewById(R.id.banner);

        new BadgeHelper((SingleActivity) getActivity()).updateBadge(BadgeHelper.BADGE.SHOP);

        pager = (ViewPagerInScrollView) view.findViewById(R.id.pager);
        scrollView = (NestedScrollView) view.findViewById(R.id.scrollView);
        pagerTabLayout = (TabLayout) view.findViewById(R.id.pagerTabStrip);
        pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(2);
        pagerAdapter.notifyDataSetChanged();

        pagerTabLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        drawBanner();

        pagerTabLayout.setupWithViewPager(pager);
        if (activity != null) {
            activity.setToolbarTransparent(true);
        }
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (activity != null) {
                    if (scrollY >= toolbarTransparency)
                        activity.setToolbarTransparency(0xFF);
                    else
                        activity.setToolbarTransparency((int)(0xFF - (toolbarTransparency - scrollY) / toolbarTransparency * 0xFF));
                }
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (activity != null) {
            activity.setToolbarTransparent(false);
        }
    }

    private void drawBanner() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(FIREBASE_DB_URL);
        if (bannerName != null) {
            StorageReference storageReference = storageRef.child(bannerName);

            // Load the image using Glide
            Glide.with(getContext())
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(banner);
        }

    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MenuFragment.newInstance(organizationName);
                case 1:
                    return AdditionalInfoFragment.newInstance(organizationName);
                case 2:
                    return FeedbackFragment.newInstance(organizationName);
                default:
                    return MenuFragment.newInstance(organizationName);
            }
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Меню";
                case 1:
                    return "Доп.Инфо";
                case 2:
                    return "Отзывы";
                default:
                    return "хз";
            }
        }

    }
}