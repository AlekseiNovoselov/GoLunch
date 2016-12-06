package golunch.mail.ru.golunch.buy;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import golunch.mail.ru.golunch.screens.dishes_list.Dish;

public class BuyHelper {

    public static final String DISH_LIST = "DISH_LIST";
    public static final String ORGANIZATION_NAME = "ORGANIZATION_NAME";
    private Context mContext;

    public BuyHelper(Context context) {
        mContext = context;
    }

    public void saveDishList(List<Dish> dishList) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dishList);
        prefsEditor.putString(DISH_LIST, json);
        prefsEditor.apply();
    }

    public List<Dish> getDishList() {
        Type type = new TypeToken<List<Dish>>(){}.getType();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        String jsonDishes = appSharedPrefs.getString(DISH_LIST, "");
        Gson gson = new Gson();
        return gson.fromJson(jsonDishes, type);
    }

    public void saveSelectedOrganizationName(String name) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.putString(ORGANIZATION_NAME, name);
        prefsEditor.apply();
    }

    public String getCurrentOrganizationName() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        return appSharedPrefs.getString(ORGANIZATION_NAME, null);
    }
}
