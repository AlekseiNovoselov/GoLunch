package golunch.mail.ru.golunch.screens.organization_item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import golunch.mail.ru.golunch.MainActivity;
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.helper.BadgeHelper;
import golunch.mail.ru.golunch.screens.base.NavigationActivity;
import golunch.mail.ru.golunch.screens.organization_item.pager.additional_info.AdditionalInfoFragment;
import golunch.mail.ru.golunch.screens.organization_item.pager.feedback.FeedbackFragment;
import golunch.mail.ru.golunch.screens.organization_item.pager.menu.MenuFragment;

import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.FIREBASE_DB_URL;
import static golunch.mail.ru.golunch.screens.organization_item.pager.BaseOrganizationFragment.ORGANIZATION_NAME;

public class OrganizationItemFragment extends Fragment {

    private static final int PAGE_COUNT = 3;

    private final static String BANNER_NAME = "BANNER_NAME";

    ViewPager pager;
    PagerAdapter pagerAdapter;
    private ImageView banner;
    private String bannerName;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) (getActivity()).findViewById(R.id.toolbar_activity_main);
        toolbar.setTitle(organizationName);
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.org_item, null);
        banner = (ImageView) view.findViewById(R.id.banner);

        new BadgeHelper((NavigationActivity) getActivity()).updateBadge(BadgeHelper.BADGE.SHOP);

        pager = (ViewPager) view.findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();

        drawBanner();

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                //Log.d(TAG, "onPageSelected, position = " + position);
                switch (position) {
                    case 0:
                        break;
                    case 1:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                // critical Section for performance
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
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
            super(getActivity().getSupportFragmentManager());
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
