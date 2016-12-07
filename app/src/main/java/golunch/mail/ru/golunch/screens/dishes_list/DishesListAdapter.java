package golunch.mail.ru.golunch.screens.dishes_list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import golunch.mail.ru.golunch.R;

import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.FIREBASE_DB_URL;

public class DishesListAdapter extends RecyclerView.Adapter<DishesListAdapter.DishViewHolder> {

    private ArrayList<Dish> dishes;
    private Context mContext;

    public DishesListAdapter(ArrayList<Dish> dishes, Context context){
        this.dishes = dishes;
        mContext = context;
    }

    public void addItem(Dish item) {
        dishes.add(item);
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dish_item, viewGroup, false);
        return new DishViewHolder(v);
    }

    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {
        holder.name.setText(dishes.get(position).getName());
        holder.weight.setText(dishes.get(position).getWeight());
        holder.price.setText(dishes.get(position).getPrice());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(FIREBASE_DB_URL);
        if (dishes.get(position).getIcon() != null) {
            StorageReference storageReference = storageRef.child(dishes.get(position).getIcon());
            // Load the image using Glide
            Glide.with(mContext)
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(holder.icon);
        }

    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public static class DishViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        ImageView icon;
        TextView name;
        TextView weight;
        TextView price;

        DishViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.rv);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            name = (TextView) itemView.findViewById(R.id.name);
            weight = (TextView) itemView.findViewById(R.id.weight);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }
}
