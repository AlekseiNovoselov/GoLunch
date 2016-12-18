package golunch.mail.ru.golunch.screens.organization_item.pager.feedback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.SingleActivity;
import golunch.mail.ru.golunch.screens.organization_item.pager.BaseOrganizationFragment;
import golunch.mail.ru.golunch.screens.organization_item.pager.menu.MenuCategory;
import golunch.mail.ru.golunch.screens.organization_item.pager.menu.MenuCategoryAdapter;
import golunch.mail.ru.golunch.screens.organizations_list.OrganizationListFragment;

public class FeedbackFragment extends BaseOrganizationFragment {

    private ArrayList<FeedBack> feedBacks;
    private FeedBackAdapter adapter;

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
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv3);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        final Button fbButton = (Button) view.findViewById(R.id.leave_feedback_button);
        fbButton.setVisibility(View.VISIBLE);
        final View fbForm =  view.findViewById(R.id.leave_feedback_view);
        fbForm.setVisibility(View.GONE);
        fbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbButton.setVisibility(View.GONE);
                fbForm.setVisibility(View.VISIBLE);
            }
        });

        feedBacks = new ArrayList<>();
        adapter = new FeedBackAdapter(feedBacks, getContext(), (SingleActivity) getActivity());
        rv.setAdapter(adapter);
        return view;
    }


    @Override
    protected void myOnChildAdded(DataSnapshot snapshot, String s) {
        FeedBack feedBack = snapshot.getValue(FeedBack.class);
        adapter.addItem(feedBack);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void getDataBaseRef() {

    }
}
