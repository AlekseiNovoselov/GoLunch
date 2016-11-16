package golunch.mail.ru.golunch.screens.bucket_list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.dishes_list.DishesListAdapter;
import golunch.mail.ru.golunch.screens.organizations_list.OrganizationListFragment;

public class BucketFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_auth, null);



        return view;
    }


}
