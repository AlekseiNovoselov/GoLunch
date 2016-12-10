package golunch.mail.ru.golunch.screens.dish_item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import golunch.mail.ru.golunch.screens.base.SingleActivity;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

public class DishActivity extends SingleActivity {

    public static final String SELECTED_DISH = "SELECTED_DISH";

    @Override
    protected Fragment getFragment() {
        Bundle intent = getIntent().getExtras();
        Dish dish = (Dish) intent.getSerializable(SELECTED_DISH);
        return DishItemFragment.newInstance(dish);
    }
}
