package golunch.mail.ru.golunch.screens.basket;

import android.support.v4.app.Fragment;
import golunch.mail.ru.golunch.screens.base.SingleActivity;

public class BasketActivity extends SingleActivity {

    @Override
    protected Fragment getFragment() {
        return BasketFragment.newInstance();
    }
}
