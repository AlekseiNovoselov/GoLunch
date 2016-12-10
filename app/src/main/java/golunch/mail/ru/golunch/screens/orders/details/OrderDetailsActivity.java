package golunch.mail.ru.golunch.screens.orders.details;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import golunch.mail.ru.golunch.screens.base.SingleActivity;


public class OrderDetailsActivity extends SingleActivity {

    public static final java.lang.String ORDER_ID = "ORDER_ID";

    @Override
    protected Fragment getFragment() {
        Bundle intent = getIntent().getExtras();
        String orderId = intent.getString(ORDER_ID);
        return OrderDetailsListFragment.newInstance(orderId);
    }
}
