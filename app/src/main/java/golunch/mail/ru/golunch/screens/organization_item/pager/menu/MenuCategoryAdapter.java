package golunch.mail.ru.golunch.screens.organization_item.pager.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.SingleActivity;

import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.FIREBASE_DB_URL;

public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryAdapter.MenuItemViewHolder>{

    private ArrayList<MenuCategory> menuCategoties;
    private Context mContext;
    private SingleActivity activity;

    public MenuCategoryAdapter(ArrayList<MenuCategory> menuCategories, Context context, SingleActivity activity){
        this.menuCategoties = menuCategories;
        mContext = context;
        this.activity = activity;
    }

    public void addItem(MenuCategory item) {
        menuCategoties.add(item);
    }

    public ArrayList<MenuCategory> getCategories() {
        return menuCategoties;
    }


    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_category_item, viewGroup, false);
        return new MenuItemViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, final int position) {
        Log.e("LLL", "text:" + menuCategoties.get(position));
        holder.name.setText(menuCategoties.get(position).name);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(FIREBASE_DB_URL);
        if (menuCategoties.get(position).icon != null) {
            StorageReference storageReference = storageRef.child(menuCategoties.get(position).icon);
            // Load the image using Glide
            Glide.with(mContext)
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(holder.icon);
        }
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedOrgCat = getCategories().get(position).orgCat;

                ArrayList<String> orgCatList = new ArrayList<>();
                ArrayList<String> categoriesList = new ArrayList<>();
                for (MenuCategory category : getCategories()) {
                    orgCatList.add(category.orgCat);
                    categoriesList.add(category.name);
                }

                activity.openDishesListScreen(selectedOrgCat, orgCatList, categoriesList);
            }
        });

    }



    @Override
    public int getItemCount() {
        return menuCategoties.size();
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView icon;
        TextView name;

        MenuItemViewHolder(View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.menu_item);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView)itemView.findViewById(R.id.category_name);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
