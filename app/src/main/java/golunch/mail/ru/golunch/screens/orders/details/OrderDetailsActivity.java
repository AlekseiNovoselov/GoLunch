package golunch.mail.ru.golunch.screens.orders.details;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.SingleActivity;


public class OrderDetailsActivity extends SingleActivity {

    public static final java.lang.String ORDER_ID = "ORDER_ID";

    @Override
    protected void includeFragment() {

        Bundle intent = getIntent().getExtras();
        String orderId = intent.getString(ORDER_ID);

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fTran = this.getSupportFragmentManager().beginTransaction();
        OrderDetailsListFragment ordersPagerFragment = OrderDetailsListFragment.newInstance(orderId);
        fTran.replace(R.id.content_main, ordersPagerFragment)
                .commit();
    }
}
