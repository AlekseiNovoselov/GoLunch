package golunch.mail.ru.golunch.screens.organization_item.pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
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

abstract public class BaseOrganizationFragment extends Fragment {

    public static final String LOG_TAG = BaseOrganizationFragment.class.getSimpleName();
    public final static String ORGANIZATION_NAME = "ORGANIZATION_NAME";

    private FirebaseApp app;
    private FirebaseAuth auth;
    protected FirebaseDatabase database;
    protected DatabaseReference databaseRef;

    protected abstract void myOnChildAdded(DataSnapshot snapshot, String s);
    protected abstract void getDataBaseRef();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        app = FirebaseApp.getInstance();
        auth = FirebaseAuth.getInstance(app);

        try {
            database = FirebaseDatabase.getInstance(app);
        } catch (DatabaseException ex) {
            Log.e("DatabaseException", ex.toString());
        }

        getDataBaseRef();

        // When the user signs in or out, update the mUsername we keep for them
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {

            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {

                }
                else {
                    Log.e(LOG_TAG, "WARNING: CurrentUser == null");
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public class OrgItemChildEventListener implements ChildEventListener {
        public void onChildAdded(DataSnapshot snapshot, String s) {
            // Get the chat message from the snapshot and add it to the UI
            myOnChildAdded(snapshot, s);
        }

        public void onChildChanged(DataSnapshot dataSnapshot, String s) { }
        public void onChildRemoved(DataSnapshot dataSnapshot) { }
        public void onChildMoved(DataSnapshot dataSnapshot, String s) { }
        public void onCancelled(DatabaseError databaseError) { }
    }

}
