package golunch.mail.ru.golunch.screens.organization_item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import golunch.mail.ru.golunch.screens.base.SingleActivity;

public class OrganizationItemActivity extends SingleActivity {

    public static final String ORGANIZATION_NAME = "ORGANIZATION_NAME";
    public static final String BANNER_NAME = "BANNER_NAME";

    @Override
    protected Fragment getFragment() {
        Bundle intent = getIntent().getExtras();
        String organizationName = intent.getString(ORGANIZATION_NAME);
        String bannerName = intent.getString(BANNER_NAME);
        return OrganizationItemFragment.newInstance(organizationName, bannerName);
    }
}
