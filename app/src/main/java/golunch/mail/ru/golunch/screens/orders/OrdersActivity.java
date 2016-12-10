package golunch.mail.ru.golunch.screens.orders;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.NavigationActivity;


public class OrdersActivity extends NavigationActivity {
    @Override
    protected Screen getScreen() {
        return Screen.ORDERS;
    }

    @Override
    protected void includeFragment() {
        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        FragmentTransaction fTran = this.getSupportFragmentManager().beginTransaction();
        OrdersPagerFragment ordersPagerFragment = OrdersPagerFragment.newInstance();
        fTran.replace(R.id.content_main, ordersPagerFragment)
                .commit();
    }
}
