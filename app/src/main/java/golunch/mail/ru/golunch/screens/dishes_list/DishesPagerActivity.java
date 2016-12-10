package golunch.mail.ru.golunch.screens.dishes_list;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import java.util.ArrayList;
import golunch.mail.ru.golunch.screens.base.SingleActivity;

public class DishesPagerActivity extends SingleActivity {

    public final static String ORG_CAT = "ORG_CAT";
    public final static String ORG_CAT_LIST = "ORG_CAT_LIST";
    public final static String CATEGORIES_LIST = "CATEGORIES_LIST";

    @Override
    protected Fragment getFragment() {
        Bundle intent = getIntent().getExtras();
        String orgCat = intent.getString(ORG_CAT);
        ArrayList<String> orgCatList = intent.getStringArrayList(ORG_CAT_LIST);
        ArrayList<String> categoriesList = intent.getStringArrayList(CATEGORIES_LIST);
        return DishesPagerFragment.newInstance(orgCat, orgCatList, categoriesList);
    }
}
