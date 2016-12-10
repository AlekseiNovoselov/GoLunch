package golunch.mail.ru.golunch.screens.dish_item;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.SingleActivity;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

public class DishActivity extends SingleActivity {

    public static final String SELECTED_DISH = "SELECTED_DISH";

    @Override
    protected void includeFragment() {
        Bundle intent = getIntent().getExtras();
        Dish dish = (Dish) intent.getSerializable(SELECTED_DISH);
        FragmentTransaction fTran = this.getSupportFragmentManager().beginTransaction();
        DishItemFragment dishesPagerFragment = DishItemFragment.newInstance(dish);
        fTran.replace(R.id.content_main, dishesPagerFragment)
                .commit();
    }
}
