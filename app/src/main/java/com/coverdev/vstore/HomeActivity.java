package com.coverdev.vstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coverdev.vstore.firebase.auth.FirebaseAuthHelper;
import com.coverdev.vstore.model.User;

public class HomeActivity extends AppCompatActivity {

    private TextView fullName;
    private Button signOut;
    private FirebaseAuthHelper firebaseAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fullName = findViewById(R.id.fullName);
        signOut = findViewById(R.id.homeSignOutBtn);
        firebaseAuthHelper = new FirebaseAuthHelper();
        firebaseAuthHelper.getCurrentUserDetails().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                User a = task.getResult();
                fullName.setText("Welcome " + a.getFullName() + "!");
            }
        });
        signOutClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!firebaseAuthHelper.isUserSignedIn()) {
            toMain();
        }
    }

    private void signOutClick() {
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuthHelper.signOut();
                toMain();
            }
        });
    }

    private void toMain() {
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(intent);
    }
}