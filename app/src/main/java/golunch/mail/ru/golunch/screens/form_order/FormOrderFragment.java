package golunch.mail.ru.golunch.screens.form_order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

import static android.provider.CallLog.Calls.NEW;
import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.ORDER_CAFE_NAME;
import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.ORDER_DISHES;
import static golunch.mail.ru.golunch.screens.orders.order_list.OrderListFragment.ORDER_STATE;

public class FormOrderFragment extends Fragment {

    private static final String LOG_TAG = FormOrderFragment.class.getSimpleName();

    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;

    private EditText mName;
    private EditText mPhone;
    private Button formOrder;

    private BuyHelper byuHelper;

    public static FormOrderFragment newInstance() {

        FormOrderFragment formOrderFragment = new FormOrderFragment();
        Bundle arguments = new Bundle();
        formOrderFragment.setArguments(arguments);
        return formOrderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.form_order, null);

        byuHelper = new BuyHelper(getContext());

        mName = (EditText) view.findViewById(R.id.name);
        mPhone = (EditText) view.findViewById(R.id.phone);
        formOrder = (Button) view.findViewById(R.id.formOrderBtn);

        formOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDataToFireBase();
            }
        });

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
        BuyHelper buyHelper = new BuyHelper(getContext());
        List<Dish> dishes = buyHelper.getDishList();
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
        BuyHelper buyHelper = new BuyHelper(getContext());
        List<Dish> dishes = buyHelper.getDishList();

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
    }

    private void showSuccessDialog() {
        Toast.makeText(getContext(), "Заказ был успешно сохранен в 'Мои Заказы'", Toast.LENGTH_SHORT).show();
    }

    private void showErrorDialog(String errorMessage) {
        Toast.makeText(getContext(), "Произошла ошибка при сохранении данных: " + errorMessage, Toast.LENGTH_SHORT).show();
    }

}
