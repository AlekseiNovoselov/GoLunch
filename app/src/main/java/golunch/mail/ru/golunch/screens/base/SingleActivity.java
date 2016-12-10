package golunch.mail.ru.golunch.screens.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import golunch.mail.ru.golunch.MainActivity;
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.helper.BadgeHelper;
import golunch.mail.ru.golunch.screens.basket.BasketActivity;
import golunch.mail.ru.golunch.screens.dish_item.DishActivity;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;
import golunch.mail.ru.golunch.screens.dishes_list.DishesPagerActivity;
import golunch.mail.ru.golunch.screens.organization_item.OrganizationItemActivity;

import static golunch.mail.ru.golunch.screens.dish_item.DishActivity.SELECTED_DISH;
import static golunch.mail.ru.golunch.screens.dishes_list.DishesPagerActivity.CATEGORIES_LIST;
import static golunch.mail.ru.golunch.screens.dishes_list.DishesPagerActivity.ORG_CAT;
import static golunch.mail.ru.golunch.screens.dishes_list.DishesPagerActivity.ORG_CAT_LIST;
import static golunch.mail.ru.golunch.screens.organization_item.OrganizationItemActivity.BANNER_NAME;
import static golunch.mail.ru.golunch.screens.organization_item.OrganizationItemActivity.ORGANIZATION_NAME;

public abstract class SingleActivity extends AppCompatActivity {

    private Menu mMenu;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        includeFragment();
    }

    protected abstract void includeFragment();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        BadgeHelper badgeHelper = new BadgeHelper(SingleActivity.this);
        badgeHelper.updateBadge(BadgeHelper.BADGE.SHOP);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_samplebadge) {
            openBasketScreen();
            return true;
        }
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public Menu getMenu() {
        return mMenu;
    }

    protected void openBasketScreen() {
        Intent intent = new Intent(this, BasketActivity.class);
        startActivity(intent);
    }

    protected void openOrganizationListScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    public void openOrganizationScreen(String organizationName, String bannerName) {
        Intent intent = new Intent(this, OrganizationItemActivity.class);
        Bundle b = new Bundle();
        b.putString(ORGANIZATION_NAME, organizationName);
        b.putString(BANNER_NAME, bannerName);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void openDishesListScreen(String selectedOrgCat, ArrayList<String> orgCatList, ArrayList<String> categoriesList) {
        Intent intent = new Intent(this, DishesPagerActivity.class);
        Bundle b = new Bundle();
        b.putString(ORG_CAT, selectedOrgCat);
        b.putStringArrayList(ORG_CAT_LIST, orgCatList);
        b.putStringArrayList(CATEGORIES_LIST, categoriesList);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void openDishScreen(Dish dish) {
        Intent intent = new Intent(this, DishActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(SELECTED_DISH, dish);
        intent.putExtras(b);
        startActivity(intent);
    }

}