package golunch.mail.ru.golunch.screens.orders.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.basket.BasketAdapter;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;
import golunch.mail.ru.golunch.screens.orders.order_list.OrderSet;

public class OrderDetailsListFragment extends Fragment {

    private ArrayList<Dish> dishes;
    private BasketAdapter adapter;
    private String orderId;

    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;


    private final static String ORDER_ID = "ORDER_ID";

    public static OrderDetailsListFragment newInstance(String orgCat) {

        OrderDetailsListFragment orderDetailsListFragment = new OrderDetailsListFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ORDER_ID, orgCat);
        orderDetailsListFragment.setArguments(arguments);
        return orderDetailsListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orderId = getArguments().getString(ORDER_ID);
        dishes = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dishes_list, null);

        dishes = new ArrayList<>();

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv3);

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        adapter = new BasketAdapter(dishes, BasketAdapter.BEHAVIOR.ORDER, null);
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
        databaseRef.orderByKey().equalTo(orderId).addChildEventListener(new MyChildEventListener() {});


        return view;
    }

    class MyChildEventListener implements ChildEventListener {
        public void onChildAdded(DataSnapshot snapshot, String s) {

            OrderSet orderSet = snapshot.getValue(OrderSet.class);
            Log.e("CAFE_NAME", "onChildAdded: " + orderSet.ORDER_CAFE_ID);
            HashMap<String, Dish> orderDishes = orderSet.ORDER_DISHES;
            if (orderDishes != null) {
                for (Map.Entry<String, Dish> entry : orderDishes.entrySet()) {
                    String key = entry.getKey();
                    Dish value = entry.getValue();
                    dishes.add(value);
                    adapter.addItem(value);
                    Log.e("FIRST_DISH_NAME", "key: " + key + " value: " + value.getName());
                }
            }
            adapter.notifyDataSetChanged();
        }

        public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
        public void onChildRemoved(DataSnapshot dataSnapshot) { }
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
        public void onCancelled(DatabaseError databaseError) { }
    }

}
