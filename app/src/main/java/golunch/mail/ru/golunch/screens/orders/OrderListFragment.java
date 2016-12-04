package golunch.mail.ru.golunch.screens.orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import golunch.mail.ru.golunch.R;

public class OrderListFragment extends Fragment {

    public static OrderListFragment newInstance() {

        OrderListFragment orderListFragment = new OrderListFragment();
        Bundle arguments = new Bundle();
        orderListFragment.setArguments(arguments);

        return orderListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dish_item_info, null);

        

        return view;
    }

    public OrderListFragment() {

    }
}
