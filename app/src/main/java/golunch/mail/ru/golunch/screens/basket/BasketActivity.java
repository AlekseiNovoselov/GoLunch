package golunch.mail.ru.golunch.screens.basket;

import android.support.v4.app.FragmentTransaction;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.SingleActivity;

public class BasketActivity extends SingleActivity {

    @Override
    protected void includeFragment() {
        FragmentTransaction fTran = this.getSupportFragmentManager().beginTransaction();
        BasketFragment basketFragment = BasketFragment.newInstance();
        fTran.replace(R.id.content_main, basketFragment)
                .commit();
    }
}
