package golunch.mail.ru.golunch.screens.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import golunch.mail.ru.golunch.MainActivity;
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.basket.BasketActivity;
import golunch.mail.ru.golunch.screens.dish_item.DishActivity;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;
import golunch.mail.ru.golunch.screens.dishes_list.DishesPagerActivity;
import golunch.mail.ru.golunch.screens.orders.OrdersActivity;
import golunch.mail.ru.golunch.screens.organization_item.OrganizationItemActivity;

import static golunch.mail.ru.golunch.screens.dish_item.DishActivity.SELECTED_DISH;
import static golunch.mail.ru.golunch.screens.dishes_list.DishesPagerActivity.CATEGORIES_LIST;
import static golunch.mail.ru.golunch.screens.dishes_list.DishesPagerActivity.ORG_CAT;
import static golunch.mail.ru.golunch.screens.dishes_list.DishesPagerActivity.ORG_CAT_LIST;
import static golunch.mail.ru.golunch.screens.organization_item.OrganizationItemActivity.BANNER_NAME;
import static golunch.mail.ru.golunch.screens.organization_item.OrganizationItemActivity.ORGANIZATION_NAME;

public abstract class NavigationActivity extends SingleActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Screen mScreen;

    public enum Screen {
        ORGANIZATION_LIST,
        ORGANIZATION_ITEM,
        DISHES_LIST,
        DISH_ITEM,
        BASKET,
        ORDERS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScreen = getScreen();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        includeFragment();
    }

    protected abstract Screen getScreen();

    protected abstract void includeFragment();

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_orders) {
            openOrdersScreen();

        } else if (id == R.id.liked) {

        } else if (id == R.id.settings) {

        } else if (id == R.id.login) {

        } else if (id == R.id.organizations) {
            if (mScreen != Screen.ORGANIZATION_LIST) {
                openOrganizationListScreen();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openOrdersScreen() {
        Intent intent = new Intent(this, OrdersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (mScreen == Screen.ORDERS) {
                openOrganizationListScreen();
                return;
            }
            super.onBackPressed();
        }
    }

}
