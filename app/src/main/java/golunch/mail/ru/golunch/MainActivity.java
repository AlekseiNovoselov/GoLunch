package golunch.mail.ru.golunch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import golunch.mail.ru.golunch.helper.GoogleAuthActivity;

public class MainActivity extends GoogleAuthActivity {

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
    }

    private void signOut() {
        // Google+ signOut
        mAuth.signOut();
    }
}
