package golunch.mail.ru.golunch.screens.basket;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.buy.BuyHelper;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;
import golunch.mail.ru.golunch.screens.organizations_list.OrganizationListFragment;

public class BasketFragment extends Fragment {

    private List<Dish> dishes;
    private BasketAdapter adapter;


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

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.addOnItemTouchListener(
                new OrganizationListFragment.RecyclerItemClickListener(getContext(), rv ,new OrganizationListFragment.RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        BuyHelper buyHelper = new BuyHelper(getContext());
        dishes = buyHelper.getDishList();

        adapter = new BasketAdapter(dishes, getContext());
        rv.setAdapter(adapter);

        return view;
    }
}
