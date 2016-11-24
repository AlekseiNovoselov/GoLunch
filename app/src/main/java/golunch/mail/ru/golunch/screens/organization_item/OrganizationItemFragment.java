package golunch.mail.ru.golunch.screens.organization_item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import golunch.mail.ru.golunch.MainActivity;
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.organization_item.pager.additional_info.AdditionalInfoFragment;
import golunch.mail.ru.golunch.screens.organization_item.pager.feedback.FeedbackFragment;
import golunch.mail.ru.golunch.screens.organization_item.pager.menu.MenuFragment;

import static golunch.mail.ru.golunch.screens.organization_item.pager.BaseOrganizationFragment.ORGANIZATION_NAME;

public class OrganizationItemFragment extends Fragment {

    private static final int PAGE_COUNT = 3;
    ViewPager pager;
    PagerAdapter pagerAdapter;
    TabLayout tabLayout;

    String organizationName;

    public static OrganizationItemFragment newInstance(String organizationName) {

        OrganizationItemFragment organizationItemFragment = new OrganizationItemFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ORGANIZATION_NAME, organizationName);
        organizationItemFragment.setArguments(arguments);
        return organizationItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        organizationName = getArguments().getString(ORGANIZATION_NAME);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.org_item, null);
        if (getActivity() != null)
            ((MainActivity) getActivity()).setToolbarTransparent(true);

        pager = (ViewPager) view.findViewById(R.id.pager);
        setupViewPager(pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(MenuFragment.newInstance(organizationName), "Меню");
        adapter.addFragment(AdditionalInfoFragment.newInstance(organizationName), "Доп");
        adapter.addFragment(FeedbackFragment.newInstance(organizationName), "Отзывы");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
