package golunch.mail.ru.golunch.screens.organization_item.pager.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.organization_item.pager.BaseOrganizationFragment;

public class MenuFragment extends BaseOrganizationFragment {

    List<String> menuItems;
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

        //initializeData();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        adapter = new MenuCategoryAdapter(menuItems, getContext());
        rv.setAdapter(adapter);

        return view;
    }

    @Override
    protected void myOnChildAdded(DataSnapshot snapshot, String s) {
        MenuCategory menuName = snapshot.getValue(MenuCategory.class);
        Log.e("LEXA_LOG", "MENU_NAME: " + menuName.name);
        adapter.addItem(menuName.name);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void getDataBaseRef() {
        Log.e("LEXA_LLLLLLL", "OrgName: " + organizationName);
        databaseRef = database.getReference("menu_categoty");
        // Listen for when child nodes get added to the collection
        databaseRef.orderByChild("organizationName")
                .equalTo(organizationName)
                .addChildEventListener(new OrgItemChildEventListener() {});

    }
}
