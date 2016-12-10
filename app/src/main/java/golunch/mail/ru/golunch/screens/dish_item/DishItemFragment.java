package golunch.mail.ru.golunch.screens.dish_item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import golunch.mail.ru.golunch.MainActivity;
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.buy.BuyHelper;
import golunch.mail.ru.golunch.helper.BadgeHelper;
import golunch.mail.ru.golunch.screens.base.NavigationActivity;
import golunch.mail.ru.golunch.screens.base.SingleActivity;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.FIREBASE_DB_URL;
import static golunch.mail.ru.golunch.screens.dish_item.DishActivity.SELECTED_DISH;

public class DishItemFragment extends Fragment {

    private TextView mFullName;
    private TextView mPrice;
    private TextView mWright;
    private TextView mComposition;
    private TextView mDescription;
    private ImageView mAddButton;
    private ImageView banner;

    private BadgeHelper badgeHelper;

    private Dish mDish;
    private BuyHelper buyHelper;

    public static DishItemFragment newInstance(Dish dish) {

        DishItemFragment dishItemFragment = new DishItemFragment();
        Bundle arguments = new Bundle();
        arguments.putSerializable(SELECTED_DISH, dish);
        dishItemFragment.setArguments(arguments);

        return dishItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDish = (Dish) getArguments().getSerializable(SELECTED_DISH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.dish_item_info, null);

        banner = (ImageView) view.findViewById(R.id.dishBanner);

        buyHelper = new BuyHelper(getContext());
        badgeHelper = new BadgeHelper((SingleActivity)getActivity());

        mFullName = (TextView) view.findViewById(R.id.fullName);
        mPrice = (TextView) view.findViewById(R.id.price);
        mWright = (TextView) view.findViewById(R.id.weight);
        mComposition = (TextView) view.findViewById(R.id.composition_value);
        mDescription = (TextView) view.findViewById(R.id.description_value);
        mAddButton = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);

        mFullName.setText(mDish.getName());
        mPrice.setText(mDish.getPrice());
        mWright.setText(mDish.getWeight());
        mComposition.setText(mDish.getComposition());
        mDescription.setText(mDish.getDescription());
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Dish> dishList = buyHelper.getDishList();
                if (dishList == null || dishList.size() == 0) {
                    dishList = new ArrayList<Dish>();
                }
                dishList.add(mDish);
                buyHelper.saveDishList(dishList);
                badgeHelper.updateBadge(BadgeHelper.BADGE.SHOP);
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(FIREBASE_DB_URL);
        if (mDish.getBannerName() != null) {
            StorageReference storageReference = storageRef.child(mDish.getBannerName());
            Glide.with(getContext())
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(banner);
        }


        return view;
    }


    @Override
    public void onResume() {
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) (getActivity()).findViewById(R.id.toolbar_activity_main);
        toolbar.setTitle(mDish.getName());
        super.onResume();
    }
}
