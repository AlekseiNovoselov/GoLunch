package golunch.mail.ru.golunch.screens.organization_item.pager.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.NavigationActivity;
import golunch.mail.ru.golunch.screens.base.SingleActivity;
import golunch.mail.ru.golunch.screens.dishes_list.DishesPagerFragment;
import golunch.mail.ru.golunch.screens.organization_item.pager.BaseOrganizationFragment;
import golunch.mail.ru.golunch.screens.organizations_list.OrganizationListFragment;

public class MenuFragment extends BaseOrganizationFragment {

    ArrayList<MenuCategory> menuItems;
    private MenuCategoryAdapter adapter;
    String organizationName;

    public static MenuFragment newInstance(String organizationName) {

        MenuFragment menuFragment = new MenuFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ORGANIZATION_NAME, organizationName);
        menuFragment.setArguments(arguments);

        return menuFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        organizationName = getArguments().getString(ORGANIZATION_NAME);
        menuItems = new ArrayList<>();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.org_item_menu, null);

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv2);
        rv.addOnItemTouchListener(
                new OrganizationListFragment.RecyclerItemClickListener(getContext(), rv ,new OrganizationListFragment.RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String selectedOrgCat = adapter.getCategories().get(position).orgCat;

                        ArrayList<String> orgCatList = new ArrayList<>();
                        ArrayList<String> categoriesList = new ArrayList<>();
                        for (MenuCategory category : adapter.getCategories()) {
                            orgCatList.add(category.orgCat);
                            categoriesList.add(category.name);
                        }

                        ((SingleActivity) (getActivity())).openDishesListScreen(selectedOrgCat, orgCatList, categoriesList);
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        adapter = new MenuCategoryAdapter(menuItems, getContext());
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    protected void myOnChildAdded(DataSnapshot snapshot, String s) {
        MenuCategory menuCategory = snapshot.getValue(MenuCategory.class);
        adapter.addItem(menuCategory);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void getDataBaseRef() {
        databaseRef = database.getReference("menu_categoty");
        // Listen for when child nodes get added to the collection
        databaseRef.orderByChild("organizationName")
                .equalTo(organizationName)
                .addChildEventListener(new OrgItemChildEventListener() {});

    }
}
