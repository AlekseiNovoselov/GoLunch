package golunch.mail.ru.golunch.screens.organization_item.pager.additional_info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.organization_item.pager.BaseOrganizationFragment;
import golunch.mail.ru.golunch.screens.organizations_list.Organization;

public class AdditionalInfoFragment extends BaseOrganizationFragment {

    private Organization organization;

    private TextView description;

    public static AdditionalInfoFragment newInstance(String organizationName) {

        AdditionalInfoFragment additionalInfoFragment = new AdditionalInfoFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ORGANIZATION_NAME, organizationName);
        additionalInfoFragment.setArguments(arguments);
        return additionalInfoFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.org_item_additional_info, null);
        description = (TextView) view.findViewById(R.id.description);

        return view;
    }

    @Override
    protected void myOnChildAdded(DataSnapshot snapshot, String s) {
        organization = snapshot.getValue(Organization.class);
        description.setText(organization.description);
    }

    @Override
    protected void getDataBaseRef() {
        databaseRef = database.getReference("cafe_list");
        // Listen for when child nodes get added to the collection
        databaseRef.orderByChild("name")
                .equalTo(getArguments().getString(ORGANIZATION_NAME))
                .addChildEventListener(new OrgItemChildEventListener() {});
    }
}
