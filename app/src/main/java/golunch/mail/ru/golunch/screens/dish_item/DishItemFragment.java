package golunch.mail.ru.golunch.screens.dish_item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

public class DishItemFragment extends Fragment {

    private TextView mFullName;
    private TextView mPrice;
    private TextView mWright;
    private TextView mComposition;
    private TextView mDescription;

    private static final String SELECTED_DISH = "SELECTED_DISH";
    private Dish mDish;

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

        mFullName = (TextView) view.findViewById(R.id.fullName);
        mPrice = (TextView) view.findViewById(R.id.price);
        mWright = (TextView) view.findViewById(R.id.weight);
        mComposition = (TextView) view.findViewById(R.id.composition_value);
        mDescription = (TextView) view.findViewById(R.id.description_value);

        mFullName.setText(mDish.getName());
        mPrice.setText(mDish.getPrice());
        mWright.setText(mDish.getWeight());
//        mComposition.setText(mDish.getComposition());
//        mDescription.setText(mDish.getCDescription());

        return view;
    }

}
