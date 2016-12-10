package golunch.mail.ru.golunch;

import android.support.v4.app.Fragment;
import golunch.mail.ru.golunch.screens.base.NavigationActivity;
import golunch.mail.ru.golunch.screens.organizations_list.OrganizationListFragment;

public class MainActivity extends NavigationActivity {

    @Override
    protected Fragment getFragment() {
        return OrganizationListFragment.newInstance();
    }
}
