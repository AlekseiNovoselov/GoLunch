package golunch.mail.ru.golunch.screens.form_order;

import android.support.v4.app.Fragment;

import golunch.mail.ru.golunch.screens.base.SingleActivity;

public class FormOrderActivity extends SingleActivity {

    @Override
    protected Fragment getFragment() {
        return FormOrderFragment.newInstance();
    }
}
