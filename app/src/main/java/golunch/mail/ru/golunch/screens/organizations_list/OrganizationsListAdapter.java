package golunch.mail.ru.golunch.screens.organizations_list;

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

import java.util.List;

import golunch.mail.ru.golunch.R;

import static golunch.mail.ru.golunch.firebase.FireBaseConfiguration.FIREBASE_DB_URL;


public class OrganizationsListAdapter extends RecyclerView.Adapter<OrganizationsListAdapter.OrgListViewHolder>{

    private List<Organization> organizations;
    private Context mContext;

    public OrganizationsListAdapter(List<Organization> organizations, Context context){
        this.organizations = organizations;
        mContext = context;
    }

    public void addItem(Organization item) {
        organizations.add(item);
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }


    public static class OrgListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        ImageView photoName;

        OrgListViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            photoName = (ImageView)itemView.findViewById(R.id.photoName);
        }
    }

    @Override
    public OrgListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.organisations_list_item, viewGroup, false);
        OrgListViewHolder pvh = new OrgListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(OrgListViewHolder holder, int position) {
        holder.name.setText(organizations.get(position).name);
        holder.description.setText(organizations.get(position).description);

        // Reference to an image file in Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(FIREBASE_DB_URL);
        if (organizations.get(position).photoName != null) {
            StorageReference storageReference = storageRef.child(organizations.get(position).photoName);

            // ImageView in your Activity
            ImageView imageView = holder.photoName;

            // Load the image using Glide
            Glide.with(mContext)
                    .using(new FirebaseImageLoader())
                    .load(storageReference)
                    .into(imageView);
        }

    }

    @Override
    public int getItemCount() {
        return organizations.size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}