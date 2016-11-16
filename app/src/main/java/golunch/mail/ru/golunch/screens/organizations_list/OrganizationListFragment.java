package golunch.mail.ru.golunch.screens.organizations_list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import golunch.mail.ru.golunch.R;
import golunch.mail.ru.golunch.screens.organization_item.OrganizationItemFragment;

public class OrganizationListFragment extends Fragment {

    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;

    private List<Organization> organizations;
    private RVAdapter adapter;


    public static OrganizationListFragment newInstance() {
        OrganizationListFragment organizationListFragment = new OrganizationListFragment();
        Bundle arguments = new Bundle();
        organizationListFragment.setArguments(arguments);
        return organizationListFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.organization_list_fragment, null);

        final RecyclerView rv = (RecyclerView) view.findViewById(R.id.rv);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), rv ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {

                        String name = adapter.getOrganizations().get(position).name;

                        FragmentTransaction fTran = getActivity().getSupportFragmentManager().beginTransaction();
                        OrganizationItemFragment organizationItemFragment = OrganizationItemFragment.newInstance(name);
                        fTran.replace(R.id.content_main, organizationItemFragment)
                                .addToBackStack(null)
                                .commit();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        //initializeData();
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        organizations = new ArrayList<>();
        adapter = new RVAdapter(organizations, getContext());
        rv.setAdapter(adapter);

        app = FirebaseApp.getInstance();
        auth = FirebaseAuth.getInstance(app);

        try {
            database = FirebaseDatabase.getInstance(app);
            FirebaseDatabase.getInstance(app).setPersistenceEnabled(true);
        } catch (DatabaseException ex) {
            Log.e("Catch", "DatabaseException");
        }

        databaseRef = database.getReference("cafe_list");
        // Listen for when child nodes get added to the collection
        databaseRef.limitToLast(100).addChildEventListener(new MyChildEventListener() {});

        // When the user signs in or out, update the mUsername we keep for them
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                //Log.e(TAG, "onAuthStateChanged");
                if (firebaseAuth.getCurrentUser() != null) {
                    //Log.e(TAG, "CurrentUser != null");
                }
                else {
                    Log.e("LexaLOG", "WARNING: CurrentUser == null");
                }
            }
        });


        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    //Log.e("connectedRef", "connected");
                    databaseRef.onDisconnect();
                    databaseRef = database.getReference("chat_2v");
                    // Listen for when child nodes get added to the collection
                    if (adapter.getItemCount() == 0) {
                        databaseRef.limitToLast(100)
                                .addChildEventListener(new MyChildEventListener());

                    }
                } else {
                    //Log.e("connectedRef", "NOT connected");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                //Log.e("connectedRef", "Listener was cancelled");
            }
        });


        return view;
    }

    class MyChildEventListener implements ChildEventListener {
        public void onChildAdded(DataSnapshot snapshot, String s) {
            //Log.e("onChildAdded", "onChildAdded");
            // Get the chat message from the snapshot and add it to the UI
            Organization organization = snapshot.getValue(Organization.class);
            adapter.addItem(organization);
            adapter.notifyDataSetChanged();
        }

        public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
        public void onChildRemoved(DataSnapshot dataSnapshot) { }
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
        public void onCancelled(DatabaseError databaseError) { }
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);

            public void onLongItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && mListener != null) {
                        mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
                return true;
            }
            return false;
        }

        @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { }

        @Override
        public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){}
    }
}
