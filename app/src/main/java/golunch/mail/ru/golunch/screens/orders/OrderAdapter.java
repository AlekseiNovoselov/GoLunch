package golunch.mail.ru.golunch.screens.orders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import golunch.mail.ru.golunch.R;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder>{

    private List<OrderItem> orders;

    public OrderAdapter() {
        orders = new ArrayList<>();
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_item, viewGroup, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.orderId.setText(orders.get(position).getOrderId());
        holder.organizationName.setText(orders.get(position).getOrganizationName());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void addItem(OrderItem orderItem) {
        orders.add(orderItem);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView orderId;
        TextView organizationName;

        public OrderViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.rv);
            orderId = (TextView)itemView.findViewById(R.id.orderId);
            organizationName = (TextView)itemView.findViewById(R.id.organization_name);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
