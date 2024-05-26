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

public class RegisterActivity extends AppCompatActivity {

    private TextView firstNameTxtField, lastNameTxtField, emailTxtField, passwordTxtField, confirmPasswordTxtField;
    private Button signUp;
    private FirebaseAuthHelper firebaseAuthHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameTxtField = findViewById(R.id.firstNameTxtField);
        lastNameTxtField = findViewById(R.id.lastNameTxtField);
        emailTxtField = findViewById(R.id.registerEmailTxtField);
        passwordTxtField = findViewById(R.id.registerPasswordTxtField);
        confirmPasswordTxtField = findViewById(R.id.confirmPasswordTxtField);

        signUp = findViewById(R.id.registerSignUpBtn);

        firebaseAuthHelper = new FirebaseAuthHelper();

        signUpClick();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuthHelper.isUserSignedIn()) {
            toHome();
        }
    }

    private void signUpClick() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameTxtField.getText().toString();
                String lastName = lastNameTxtField.getText().toString();
                String email = emailTxtField.getText().toString();
                String password = passwordTxtField.getText().toString();
                String confirmPassword = confirmPasswordTxtField.getText().toString();

                if (password.length() < 8) {
                    Toast.makeText(RegisterActivity.this, "Password requires 8+ length.", Toast.LENGTH_LONG).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Password not match.", Toast.LENGTH_LONG).show();
                    return;
                }

                User user = new User("", firstName, lastName, email, password);

                firebaseAuthHelper.signUp(user).addOnCompleteListener(task -> {
                    boolean result = task.getResult();
                    if (result) {
                        Toast.makeText(RegisterActivity.this, "User successfully created.", Toast.LENGTH_SHORT).show();
                        toHome();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void toHome() {
        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}