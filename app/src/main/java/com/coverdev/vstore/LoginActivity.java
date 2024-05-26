package com.coverdev.vstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.coverdev.vstore.firebase.auth.FirebaseAuthHelper;
import com.coverdev.vstore.model.User;

public class LoginActivity extends AppCompatActivity {

    private TextView emailTxtField, passwordTxtField;
    private Button signIn;
    private FirebaseAuthHelper firebaseAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTxtField = findViewById(R.id.loginEmailTxtField);
        passwordTxtField = findViewById(R.id.loginPasswordTxtField);

        signIn = findViewById(R.id.loginSignInBtn);

        firebaseAuthHelper = new FirebaseAuthHelper();

        signInClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuthHelper.isUserSignedIn()) {
            toHome();
        }
    }

    private void signInClick() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxtField.getText().toString();
                String password = passwordTxtField.getText().toString();
                firebaseAuthHelper.signIn(email, password).addOnCompleteListener(task -> {
                    boolean result = task.getResult();
                    if (result) {
                        toHome();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void toHome() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}