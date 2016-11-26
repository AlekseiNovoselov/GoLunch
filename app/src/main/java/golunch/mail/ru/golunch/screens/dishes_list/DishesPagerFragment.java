package golunch.mail.ru.golunch.screens.dishes_list;

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

import java.util.ArrayList;

import golunch.mail.ru.golunch.R;

public class DishesPagerFragment extends Fragment {

    public final static String ORG_CAT = "ORG_CAT";
    public final static String ORG_CAT_LIST = "ORG_CAT_LIST";
    public final static String CATEGORIES_LIST = "CATEGORIES_LIST";


    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private String orgCat;
    private ArrayList<String> categoriesList;
    private ArrayList<String> orgCatList;

    public static DishesPagerFragment newInstance(String orgCat, ArrayList<String> orgCatList,
                                                  ArrayList<String> categoriesList) {

        DishesPagerFragment dishesListFragment = new DishesPagerFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ORG_CAT, orgCat);
        arguments.putStringArrayList(ORG_CAT_LIST, orgCatList);
        arguments.putStringArrayList(CATEGORIES_LIST, categoriesList);
        dishesListFragment.setArguments(arguments);

        return dishesListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dishes_pager, null);

        orgCat = getArguments().getString(ORG_CAT);
        orgCatList = getArguments().getStringArrayList(ORG_CAT_LIST);
        categoriesList = getArguments().getStringArrayList(CATEGORIES_LIST);

        pager = (ViewPager) view.findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();


        return view;
    }

    private class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(getActivity().getSupportFragmentManager());
        }

        @Override
        public Fragment getItem(int position) {
            return DishesListFragment.newInstance(orgCatList.get(position));
        }

        @Override
        public int getCount() {
            return orgCatList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return categoriesList.get(position);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        pagerAdapter = new DishesPagerFragment.MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
    }

}
