package golunch.mail.ru.golunch.screens.basket;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder>{

    public MyAdapterListener onClickListener;

    public interface MyAdapterListener {

        void iconClearOnClick(View v, int position);
        void iconRemoveOnClick(View v, int position);
        void iconAddOnClick(View v, int position);
    }

    private List<Dish> dishes;


    public BasketAdapter(List<Dish> dishes, MyAdapterListener listener) {
        this.dishes = dishes;
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
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
