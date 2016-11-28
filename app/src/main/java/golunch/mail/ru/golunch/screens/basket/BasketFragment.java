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
import java.util.List;

import golunch.mail.ru.golunch.MainActivity;
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.buy.BuyHelper;
import golunch.mail.ru.golunch.helper.BadgeHelper;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

public class BasketFragment extends Fragment {

    private List<Dish> dishes;
    private BasketAdapter adapter;
    private RecyclerView rv;
    private BadgeHelper badgeHelper;

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
        View view = inflater.inflate(R.layout.basket, null);

        badgeHelper = new BadgeHelper((MainActivity) getActivity());
        badgeHelper.updateBadge(BadgeHelper.BADGE.BASKET);
        rv = (RecyclerView) view.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        BuyHelper buyHelper = new BuyHelper(getContext());
        dishes = buyHelper.getDishList();

        adapter = new BasketAdapter(dishes, new BasketAdapter.MyAdapterListener() {

            @Override
            public void iconClearOnClick(View v, int position) {
                Log.d("LEXA_LOG", "Clear position "+position);
            }

            @Override
            public void iconRemoveOnClick(View v, int position) {
                Log.d("LEXA_LOG", "Remove at position "+position);
                dishes.remove(position);
                BuyHelper buyHelper = new BuyHelper(getContext());
                buyHelper.saveDishList(dishes);
                adapter.notifyDataSetChanged();
                badgeHelper.updateBadge(BadgeHelper.BADGE.BASKET);
            }

            @Override
            public void iconAddOnClick(View v, int position) {
                Log.d("LEXA_LOG", "Add at position "+position);
            }
        });
        rv.setAdapter(adapter);

        return view;
    }

}
