package golunch.mail.ru.golunch.screens.orders;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.buy.BuyHelper;
import golunch.mail.ru.golunch.helper.BadgeHelper;
import golunch.mail.ru.golunch.screens.basket.BasketAdapter;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;
import golunch.mail.ru.golunch.screens.organizations_list.Organization;
import golunch.mail.ru.golunch.screens.organizations_list.OrganizationListFragment;

import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.ORDER_DISHES;

public class OrderListFragment extends Fragment {

    private OrderAdapter adapter;
    private RecyclerView rv;

    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;

    private TextView warningMessage;
    private List<OrderItem> orderItems;

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
        View view = inflater.inflate(R.layout.my_orders_list, null);

        orderItems = new ArrayList<>();
        rv = (RecyclerView) view.findViewById(R.id.rv);
        warningMessage = (TextView) view.findViewById(R.id.warningMessageText);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        adapter = new OrderAdapter();
        rv.setAdapter(adapter);

        app = FirebaseApp.getInstance();
        auth = FirebaseAuth.getInstance(app);

        try {
            database = FirebaseDatabase.getInstance(app);
            FirebaseDatabase.getInstance(app).setPersistenceEnabled(true);
        } catch (DatabaseException ex) {
            Log.e("Catch", "DatabaseException:" + ex.toString());
        }

        databaseRef = database.getReference("orders");
        databaseRef.limitToLast(100).addChildEventListener(new MyChildEventListener() {});

        updateViewState();

        return view;
    }

    class MyChildEventListener implements ChildEventListener {
        public void onChildAdded(DataSnapshot snapshot, String s) {

            OrderSet orderSet = snapshot.getValue(OrderSet.class);
            Log.e("CAFE_NAME", "onChildAdded: " + orderSet.ORDER_CAFE_ID);
            HashMap<String, Dish> orderDishes = orderSet.ORDER_DISHES;
            for (Map.Entry<String, Dish> entry : orderDishes.entrySet()) {
                String key = entry.getKey();
                Dish value = entry.getValue();
                Log.e("FIRST_DISH_NAME", "key: " + key + " value: " + value.getName());
            }
            OrderItem orderItem = new OrderItem(snapshot.getKey(), orderSet.ORDER_CAFE_ID);
            orderItems.add(orderItem);
            adapter.addItem(orderItem);
            adapter.notifyDataSetChanged();
            updateViewState();
        }

        public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
        public void onChildRemoved(DataSnapshot dataSnapshot) { }
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
        public void onCancelled(DatabaseError databaseError) { }
    }

    private void updateViewState() {
        if (orderItems.size() == 0) {
            warningMessage.setVisibility(View.VISIBLE);
        } else {
            warningMessage.setVisibility(View.INVISIBLE);
        }
    }

}
