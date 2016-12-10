package golunch.mail.ru.golunch.screens.organization_item;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.NavigationActivity;

public class OrganizationItemActivity extends NavigationActivity {

    public static final String ORGANIZATION_NAME = "ORGANIZATION_NAME";
    public static final String BANNER_NAME = "BANNER_NAME";

    @Override
    protected Screen getScreen() {
        return Screen.ORGANIZATION_ITEM;
    }

    @Override
    protected void includeFragment() {
        Bundle intent = getIntent().getExtras();
        String organizationName = intent.getString(ORGANIZATION_NAME);
        String bannerName = intent.getString(BANNER_NAME);
        FragmentTransaction fTran = this.getSupportFragmentManager().beginTransaction();
        OrganizationItemFragment lobbyFragment = OrganizationItemFragment.newInstance(organizationName, bannerName);
        fTran.replace(R.id.content_main, lobbyFragment)
                .commit();
    }
}
