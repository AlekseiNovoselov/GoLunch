package golunch.mail.ru.golunch;

import android.support.v4.app.FragmentTransaction;
import golunch.mail.ru.golunch.screens.base.NavigationActivity;
import golunch.mail.ru.golunch.screens.organizations_list.OrganizationListFragment;

public class MainActivity extends NavigationActivity {

    @Override
    protected Screen getScreen() {
        return Screen.ORGANIZATION_LIST;
    }

    @Override
    protected void includeFragment() {
        FragmentTransaction fTran = this.getSupportFragmentManager().beginTransaction();
        OrganizationListFragment lobbyFragment = OrganizationListFragment.newInstance();
        fTran.replace(R.id.content_main, lobbyFragment)
                .commit();
    }
}
