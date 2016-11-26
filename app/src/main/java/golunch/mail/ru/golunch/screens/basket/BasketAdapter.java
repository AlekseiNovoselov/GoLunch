package golunch.mail.ru.golunch.screens.basket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.dishes_list.Dish;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.BasketViewHolder>{

    private List<Dish> dishes;
    private Context mContext;


    public BasketAdapter(List<Dish> dishes, Context context) {
        mContext = context;
        this.dishes = dishes;
    }

    @Override
    public BasketViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.basket_item, viewGroup, false);
        BasketAdapter.BasketViewHolder pvh = new BasketAdapter.BasketViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(BasketViewHolder holder, int position) {
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
        ImageView minus;
        ImageView plus;
        ImageView delete;

        public BasketViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.rv);
            preview = (ImageView)itemView.findViewById(R.id.preview);
            full_name = (TextView)itemView.findViewById(R.id.full_name);
            description = (TextView)itemView.findViewById(R.id.description);
            price = (TextView)itemView.findViewById(R.id.price);
            count = (TextView)itemView.findViewById(R.id.count);
            minus = (ImageView)itemView.findViewById(R.id.minus);
            plus = (ImageView)itemView.findViewById(R.id.plus);
            delete = (ImageView)itemView.findViewById(R.id.delete);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
