package golunch.mail.ru.golunch.screens.basket;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.buy.BuyHelper;
import golunch.mail.ru.golunch.helper.BadgeHelper;
import golunch.mail.ru.golunch.screens.base.SingleActivity;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

public class BasketFragment extends Fragment {

    private List<Dish> dishes;
    private BasketAdapter adapter;
    private RecyclerView rv;
    private BadgeHelper badgeHelper;
    private Button checkout;
    private TextView warningMessageText;

    private BuyHelper byuHelper;

    public static BasketFragment newInstance() {

        BasketFragment basketFragment = new BasketFragment();
        Bundle arguments = new Bundle();
        basketFragment.setArguments(arguments);
        return basketFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.order_list, null);

        byuHelper = new BuyHelper(getContext());

        checkout = (Button) view.findViewById(R.id.checkout);
        warningMessageText = (TextView) view.findViewById(R.id.warningMessageText);
        badgeHelper = new BadgeHelper((SingleActivity) getActivity());
        badgeHelper.updateBadge(BadgeHelper.BADGE.BASKET);
        rv = (RecyclerView) view.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        BuyHelper buyHelper = new BuyHelper(getContext());
        dishes = buyHelper.getDishList();

        adapter = new BasketAdapter(getContext(), dishes, BasketAdapter.BEHAVIOR.BASKET, new BasketAdapter.MyAdapterListener() {

            @Override
            public void iconClearOnClick(View v, int position) {

            }

            @Override
            public void iconRemoveOnClick(View v, int position) {
                dishes.remove(position);
                BuyHelper buyHelper = new BuyHelper(getContext());
                buyHelper.saveDishList(dishes);
                adapter.notifyDataSetChanged();
                badgeHelper.updateBadge(BadgeHelper.BADGE.BASKET);
                updateView();
            }

            @Override
            public void iconAddOnClick(View v, int position) {

            }
        });

        updateView();
        rv.setAdapter(adapter);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((SingleActivity) getActivity()).openFormOrderScreen();
            }
        });


        return view;
    }

    private void updateView() {
        if (dishes.size() == 0) {
            checkout.setVisibility(View.INVISIBLE);
            warningMessageText.setVisibility(View.VISIBLE);
        } else {
            warningMessageText.setVisibility(View.INVISIBLE);
            checkout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) (getActivity()).findViewById(R.id.toolbar_activity_main);
        toolbar.setTitle("Корзина");
        super.onResume();
    }
}
