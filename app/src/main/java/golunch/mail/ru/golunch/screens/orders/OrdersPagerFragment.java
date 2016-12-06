package golunch.mail.ru.golunch.screens.orders;

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


import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.firebase.FireBaseConfiguration;
import golunch.mail.ru.golunch.screens.orders.order_list.OrderListFragment;

public class OrdersPagerFragment extends Fragment {

    private static final int PAGE_COUNT = 3;

    private ViewPager pager;
    private PagerAdapter pagerAdapter;


    public static OrdersPagerFragment newInstance() {

        OrdersPagerFragment ordersPagerFragment = new OrdersPagerFragment();
        Bundle arguments = new Bundle();
        ordersPagerFragment.setArguments(arguments);

        return ordersPagerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dishes_pager, null);

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
            FireBaseConfiguration.ORDER_STATE_ENUM state;
            switch (position) {
                case 0:
                    state = FireBaseConfiguration.ORDER_STATE_ENUM.NEW;
                    break;
                case 1:
                    state = FireBaseConfiguration.ORDER_STATE_ENUM.PAID;
                    break;
                case 2:
                    state = FireBaseConfiguration.ORDER_STATE_ENUM.CANCELLED;
                    break;
                default:
                    state = FireBaseConfiguration.ORDER_STATE_ENUM.CANCELLED;
            }
            return OrderListFragment.newInstance(state);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Ожидаемые";
                case 1:
                    return "Оплаченные";
                case 2:
                    return "Отмененные";
                default:
                    return "хз";
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        pagerAdapter = new OrdersPagerFragment.MyFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
    }

}