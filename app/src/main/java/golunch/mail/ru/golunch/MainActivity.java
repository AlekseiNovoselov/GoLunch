package golunch.mail.ru.golunch;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;

import golunch.mail.ru.golunch.screens.base.NavigationActivity;
import golunch.mail.ru.golunch.screens.organizations_list.OrganizationListFragment;

public class MainActivity extends NavigationActivity {

    @Override
    protected Fragment getFragment() {
        return OrganizationListFragment.newInstance();
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}

