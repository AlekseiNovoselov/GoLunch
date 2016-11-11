package golunch.mail.ru.golunch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

import golunch.mail.ru.golunch.helper.GoogleAuthActivity;

public class MainActivity extends GoogleAuthActivity {

    private FirebaseApp app;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private DatabaseReference databaseRef;

    private List<Organization> organizations;
    private RVAdapter adapter;

    @Override
    protected void onMyAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(MainActivity.this, AuthActivity.class));
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button logOutBtn = (Button) findViewById(R.id.logOutBtn);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);

        //initializeData();
        LinearLayoutManager llm = new LinearLayoutManager(getBaseContext());
        rv.setLayoutManager(llm);

        organizations = new ArrayList<>();
        adapter = new RVAdapter(organizations, getBaseContext());
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

    }

    private void signOut() {
        // Google+ signOut
        mAuth.signOut();
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

}
