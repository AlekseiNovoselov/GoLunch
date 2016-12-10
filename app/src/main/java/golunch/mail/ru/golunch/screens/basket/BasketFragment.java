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

import golunch.mail.ru.golunch.MainActivity;
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.buy.BuyHelper;
import golunch.mail.ru.golunch.helper.BadgeHelper;
import golunch.mail.ru.golunch.screens.base.NavigationActivity;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

import static android.provider.CallLog.Calls.NEW;
import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.ORDER_DISHES;
import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.ORDER_CAFE_NAME;
import static golunch.mail.ru.golunch.screens.orders.order_list.OrderListFragment.ORDER_STATE;

public class BasketFragment extends Fragment {

    public final static String LOG_TAG = BasketFragment.class.getSimpleName();

    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;

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
        badgeHelper = new BadgeHelper((NavigationActivity) getActivity());
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

        app = FirebaseApp.getInstance();
        auth = FirebaseAuth.getInstance(app);

        try {
            database = FirebaseDatabase.getInstance(app);
            FirebaseDatabase.getInstance(app).setPersistenceEnabled(true);
        } catch (DatabaseException ex) {
            Log.e("Catch", "DatabaseException:" + ex.toString());
        }

        databaseRef = database.getReference("orders");

        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                //Log.e(TAG, "onAuthStateChanged");
                if (firebaseAuth.getCurrentUser() != null) {
                    //Log.e(TAG, "CurrentUser != null");
                }
                else {
                    Log.e("LexaLOG", "WARNING: CurrentUser == null");
                }
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDataToFireBase();
            }
        });


        return view;
    }

    private void addDataToFireBase() {
        final DatabaseReference ref = databaseRef.push();
        Map<String, String> cafeField= new HashMap<>();
        String organizationName = byuHelper.getCurrentOrganizationName();
        if (organizationName == null) {
            Log.e(LOG_TAG, "organizationName == null");
            showErrorDialog("Не выбрано заведение");
        }
        if (dishes.size() == 0) {
            showErrorDialog("Ничего не выбрано");
        }
        cafeField.put(ORDER_CAFE_NAME, organizationName);
        cafeField.put(ORDER_STATE, NEW.toUpperCase());
        ref.setValue(cafeField , new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    showErrorDialog(databaseError.getMessage());
                } else {
                    saveDishesList(ref);
                }
            }
        });
    }

    private void saveDishesList(DatabaseReference ref) {
        DatabaseReference dishesRef = ref.child(ORDER_DISHES);
        final int[] j = {0};
        final int dishesSize = dishes.size();
        for(int i = 0; i < dishes.size(); i++) {
            dishesRef.push().setValue(dishes.get(i), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if (databaseError != null) {
                        showErrorDialog(databaseError.getMessage());
                    } else {
                        Log.e(LOG_TAG, "Data saved successfully.");
                        j[0]++;
                        if (j[0] == dishesSize) {
                            showSuccessDialog();
                            clearOrderData();
                        }
                    }
                }
            });
        }
    }

    private void clearOrderData() {
        ArrayList<Dish> dishes = new ArrayList<>();
        byuHelper.saveDishList(dishes);
        adapter.clearData();
        adapter.notifyDataSetChanged();
        updateView();
    }

    private void showSuccessDialog() {
        Toast.makeText(getContext(), "Заказ был успешно сохранен в 'Мои Заказы'", Toast.LENGTH_SHORT).show();
    }

    private void showErrorDialog(String errorMessage) {
        Toast.makeText(getContext(), "Произошла ошибка при сохранении данных: " + errorMessage, Toast.LENGTH_SHORT).show();
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
