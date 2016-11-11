package golunch.mail.ru.golunch;

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


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{


    private List<Organization> organizations;
    private Context mContext;

    public RVAdapter(List<Organization> organizations, Context context){
        this.organizations = organizations;
        mContext = context;
    }

    public void addItem(Organization item) {
        organizations.add(item);
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        RecyclerView rv;
        TextView name;
        TextView description;
        ImageView photoName;

        PersonViewHolder(View itemView) {
            super(itemView);
            rv = (RecyclerView) itemView.findViewById(R.id.rv);
            name = (TextView)itemView.findViewById(R.id.name);
            description = (TextView)itemView.findViewById(R.id.description);
            photoName = (ImageView)itemView.findViewById(R.id.photoName);
        }
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.name.setText(organizations.get(position).name);
        holder.description.setText(organizations.get(position).description);

        // Reference to an image file in Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://golunch-11dd2.appspot.com/");
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