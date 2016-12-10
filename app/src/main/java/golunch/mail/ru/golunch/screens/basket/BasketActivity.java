package golunch.mail.ru.golunch.screens.basket;

import android.support.v4.app.FragmentTransaction;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.NavigationActivity;

public class BasketActivity extends NavigationActivity {

    @Override
    protected Screen getScreen() {
        return Screen.BASKET;
    }

    @Override
    protected void includeFragment() {
        FragmentTransaction fTran = this.getSupportFragmentManager().beginTransaction();
        BasketFragment basketFragment = BasketFragment.newInstance();
        fTran.replace(R.id.content_main, basketFragment)
                .commit();
    }
}
