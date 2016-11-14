package golunch.mail.ru.golunch.screens.organization_item.pager.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.organization_item.pager.BaseOrganizationFragment;

public class FeedbackFragment extends BaseOrganizationFragment {

    private List<FeedBack> feedBacks;

    public static FeedbackFragment newInstance(String organizationName) {

        FeedbackFragment feedbackFragment = new FeedbackFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ORGANIZATION_NAME, organizationName);
        feedbackFragment.setArguments(arguments);
        return feedbackFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.org_item_feedback, null);
        return view;
    }


    @Override
    protected void myOnChildAdded(DataSnapshot snapshot, String s) {
        //organization = snapshot.getValue(Organization.class);
        // TODO - feedBack
        //FeedBack feedBack = snapshot.getValue(FeedBack.class);
        //feedBacks.add(feedBack);
    }

    @Override
    protected void getDataBaseRef() {

    }
}
