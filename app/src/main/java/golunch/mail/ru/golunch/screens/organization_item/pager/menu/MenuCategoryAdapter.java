package golunch.mail.ru.golunch.screens.organization_item.pager.menu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import golunch.mail.ru.golunch.R;

public class MenuCategoryAdapter extends RecyclerView.Adapter<MenuCategoryAdapter.MenuItemViewHolder>{

    private List<String> menuCategoties;
    private Context mContext;

    public MenuCategoryAdapter(List<String> menuCategories, Context context){
        this.menuCategoties = menuCategories;
        mContext = context;
    }

    public void addItem(String item) {
        menuCategoties.add(item);
    }

    public List<String> getCategories() {
        return menuCategoties;
    }


    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_category_item, viewGroup, false);
        return new MenuItemViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, int position) {
        Log.e("LLL", "text:" + menuCategoties.get(position));
        holder.name.setText(menuCategoties.get(position));
    }

    @Override
    public int getItemCount() {
        return menuCategoties.size();
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView name;

        MenuItemViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.rv);
            name = (TextView)itemView.findViewById(R.id.category_name);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
