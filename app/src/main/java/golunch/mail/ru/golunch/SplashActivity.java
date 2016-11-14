package golunch.mail.ru.golunch;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

import golunch.mail.ru.golunch.helper.GoogleAuthActivity;

public class SplashActivity extends GoogleAuthActivity {

    @Override
    protected void onMyAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkAccount();
        finish();
    }

    private void checkAccount() {
//        if (mAuth.getCurrentUser() != null) {
//            startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        } else {
//            startActivity(new Intent(SplashActivity.this, AuthActivity.class));
//        }
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }
}
