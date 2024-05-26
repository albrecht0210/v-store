package com.coverdev.vstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.coverdev.vstore.firebase.auth.FirebaseAuthHelper;
import com.coverdev.vstore.util.DatabaseUtil;

public class MainActivity extends AppCompatActivity {

    private Button signIn, signUp;
    private FirebaseAuthHelper firebaseAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signIn = findViewById(R.id.signInBtn);
        signUp = findViewById(R.id.signUpBtn);

        firebaseAuthHelper = new FirebaseAuthHelper();

        openIntent(signIn, LoginActivity.class);
        openIntent(signUp, RegisterActivity.class);
        DatabaseUtil.initializeDatabase(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuthHelper.isUserSignedIn()) {
            toHome();
        }
    }

    private void openIntent(Button button, Class<?> cls) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, cls);
                startActivity(intent);
            }
        });
    }

    private void toHome() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}