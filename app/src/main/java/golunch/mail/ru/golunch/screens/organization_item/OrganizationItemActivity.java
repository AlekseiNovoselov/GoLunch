package golunch.mail.ru.golunch.screens.organization_item;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.SingleActivity;

public class OrganizationItemActivity extends SingleActivity {

    public static final String ORGANIZATION_NAME = "ORGANIZATION_NAME";
    public static final String BANNER_NAME = "BANNER_NAME";

    Toolbar toolbar;
    View statusBar;
    @Override
    protected Fragment getFragment() {
        Bundle intent = getIntent().getExtras();
        String organizationName = intent.getString(ORGANIZATION_NAME);
        String bannerName = intent.getString(BANNER_NAME);
        return OrganizationItemFragment.newInstance(organizationName, bannerName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_item_activity);

        toolbar = (Toolbar) findViewById(R.id.toolbar_activity_organizationitem);
        statusBar = (View) findViewById(R.id.statusBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }
        FragmentTransaction fTran = this.getSupportFragmentManager().beginTransaction();
        fTran.replace(R.id.content_main, getFragment())
                .commit();
    }

    public void setToolbarTransparent(boolean transparent) {
        if (transparent) {
            if (toolbar != null)
                toolbar.getBackground().setAlpha(0);
            if (statusBar != null)
                statusBar.getBackground().setAlpha(0);
        }
        else {
            if (toolbar != null)
                toolbar.getBackground().setAlpha(0xFF);
            if (statusBar != null)
                statusBar.getBackground().setAlpha(0xFF);
        }
    }
    public void setToolbarTransparency(int transparentcy) {
        if (toolbar != null)
            toolbar.getBackground().setAlpha(transparentcy);
        if (statusBar != null)
            statusBar.getBackground().setAlpha(transparentcy);
    }

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
