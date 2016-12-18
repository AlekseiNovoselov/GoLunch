package golunch.mail.ru.golunch.screens.organization_item.pager.feedback;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.base.SingleActivity;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.MenuItemViewHolder>{

    private ArrayList<FeedBack> feedBacks;
    private Context mContext;
    private SingleActivity activity;

    public FeedBackAdapter(ArrayList<FeedBack> menuCategories, Context context, SingleActivity activity){
        this.feedBacks = menuCategories;
        mContext = context;
        this.activity = activity;
    }

    public void addItem(FeedBack item) {
        feedBacks.add(item);
    }

    public ArrayList<FeedBack> getFeedbacks() {
        return feedBacks;
    }


    @Override
    public MenuItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_category_item, viewGroup, false);
        return new MenuItemViewHolder(v);

    }

    @Override
    public void onBindViewHolder(MenuItemViewHolder holder, final int position) {
        FeedBack tmpFeedBack = feedBacks.get(position);
        if (tmpFeedBack.positive) {
            holder.negativePic.setVisibility(View.GONE);
            holder.positivePic.setVisibility(View.VISIBLE);
        }
        else {
            holder.negativePic.setVisibility(View.VISIBLE);
            holder.positivePic.setVisibility(View.GONE);
        }
        holder.date.setText(tmpFeedBack.date);
        holder.text.setText(tmpFeedBack.text);
        holder.user.setText(tmpFeedBack.user);
    }



    @Override
    public int getItemCount() {
        return feedBacks.size();
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {
        View positivePic, negativePic;
        TextView date, text, user;

        MenuItemViewHolder(View itemView) {
            super(itemView);
            positivePic = itemView.findViewById(R.id.feedback_good);
            negativePic = itemView.findViewById(R.id.feedback_bad);
            date = (TextView) itemView.findViewById(R.id.feedback_date);
            text = (TextView) itemView.findViewById(R.id.feedback_text);
            user = (TextView) itemView.findViewById(R.id.feedback_author);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
