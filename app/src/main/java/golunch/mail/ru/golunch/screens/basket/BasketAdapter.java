package golunch.mail.ru.golunch.screens.basket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.FIREBASE_DB_URL;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder>{

    public enum BEHAVIOR {
        BASKET, ORDER
    }

    public MyAdapterListener onClickListener;
    private BEHAVIOR behavior;

    public void addItem(Dish dish) {
        dishes.add(dish);
    }

    public interface MyAdapterListener {

        void iconClearOnClick(View v, int position);
        void iconRemoveOnClick(View v, int position);
        void iconAddOnClick(View v, int position);
    }

    private List<Dish> dishes;
    private Context context;


    public BasketAdapter(Context context, List<Dish> dishes, BEHAVIOR behavior, MyAdapterListener listener) {
        this.dishes = dishes;
        this.behavior = behavior;
        this.context = context;
        onClickListener = listener;
    }

    @Override
    public BasketViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.basket_item, viewGroup, false);
        BasketAdapter.BasketViewHolder pvh = new BasketAdapter.BasketViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final BasketViewHolder holder, final int position) {
        holder.full_name.setText(dishes.get(position).getName());
        holder.price.setText(dishes.get(position).getPrice());
        holder.description.setText(dishes.get(position).getName());

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(FIREBASE_DB_URL);
        if (dishes.get(position).getIcon() != null) {
            StorageReference storageReference = storageRef.child(dishes.get(position).getIcon());
            // Load the image using Glide
            Glide.with(context)
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(holder.preview);
        }
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public class BasketViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        ImageView preview;
        TextView full_name;
        TextView description;
        TextView price;
        TextView count;
        Button remove;
        Button add;
        Button clear;

        public BasketViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.rv);
            preview = (ImageView)itemView.findViewById(R.id.preview);
            full_name = (TextView)itemView.findViewById(R.id.full_name);
            description = (TextView)itemView.findViewById(R.id.description);
            price = (TextView)itemView.findViewById(R.id.price);
            count = (TextView)itemView.findViewById(R.id.count);
            remove = (Button)itemView.findViewById(R.id.remove);
            add = (Button)itemView.findViewById(R.id.add);
            clear = (Button)itemView.findViewById(R.id.clear);

            switch (behavior) {
                case BASKET:
                    clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickListener.iconRemoveOnClick(v, getAdapterPosition());
                        }
                    });
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onClickListener.iconClearOnClick(v, getAdapterPosition());
                        }
                    });
                    break;
                case ORDER:
                    clear.setVisibility(View.GONE);
                    add.setVisibility(View.GONE);
                    remove.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void clearData() {
        dishes.clear();
    }
}
