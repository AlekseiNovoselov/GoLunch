package golunch.mail.ru.golunch.screens.dishes_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.organization_item.pager.BaseOrganizationFragment;
import golunch.mail.ru.golunch.screens.organizations_list.OrganizationListFragment;

import static golunch.mail.ru.golunch.screens.dishes_list.DishesPagerFragment.ORG_CAT;

public class DishesListFragment extends BaseOrganizationFragment {

    private ArrayList<Dish> dishes;

    private DishesListAdapter adapter;
    private String orgCat;

    public static DishesListFragment newInstance(String orgCat) {

        DishesListFragment dishesListFragment = new DishesListFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ORG_CAT, orgCat);
        dishesListFragment.setArguments(arguments);

        return dishesListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orgCat = getArguments().getString(ORG_CAT);
        dishes = new ArrayList<>();
    }

    @Override
    protected void myOnChildAdded(DataSnapshot snapshot, String s) {
        Dish dish = snapshot.getValue(Dish.class);
        adapter.addItem(dish);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void getDataBaseRef() {
        databaseRef = database.getReference("dish");
        // Listen for when child nodes get added to the collection
        databaseRef.orderByChild("indexOrgCat")
                .equalTo(orgCat)
                .addChildEventListener(new OrgItemChildEventListener() {});

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dishes_list, null);

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv3);
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

        adapter = new DishesListAdapter(dishes, getContext());
        rv.setAdapter(adapter);

        return view;
    }
}
