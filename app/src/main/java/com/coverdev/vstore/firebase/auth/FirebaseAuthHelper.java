package com.coverdev.vstore.firebase.auth;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.coverdev.vstore.firebase.database.user.UserDatabaseHelper;
import com.coverdev.vstore.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAuthHelper {
    private FirebaseAuth firebaseAuth;
    private UserDatabaseHelper userDatabaseHelper;

    public FirebaseAuthHelper() {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userDatabaseHelper = new UserDatabaseHelper();
    }

    public Task<Boolean> signUp(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        signUp(email, password).addOnCompleteListener(task -> {
            boolean result = task.getResult();
            if (result) {
                User newUser = User.toUser(firebaseAuth.getCurrentUser(), user.getFirstName(), user.getLastName());
                userDatabaseHelper.addUser(newUser).addOnCompleteListener(taskAdd -> {
                    boolean isAdded = taskAdd.isSuccessful();
                    taskCompletionSource.setResult(isAdded);
                });
            } else {
                taskCompletionSource.setResult(false);
            }
        });

        return taskCompletionSource.getTask();
    }

    public Task<Boolean> signUp(String email, String password) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                taskCompletionSource.setResult(task.isSuccessful());
            });
        return taskCompletionSource.getTask();
    }

    public Task<Boolean> signIn(String email, String password) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                taskCompletionSource.setResult(task.isSuccessful());
            });
        return taskCompletionSource.getTask();
    }

    public void signOut() {
        firebaseAuth.signOut();
    }

    public boolean isUserSignedIn() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public Task<User> getCurrentUserDetails() {
        FirebaseUser loggedUser = firebaseAuth.getCurrentUser();

        final TaskCompletionSource<User> taskCompletionSource = new TaskCompletionSource<>();
        userDatabaseHelper.retrieveProfile(loggedUser.getUid()).addOnCompleteListener(task -> {
            User resultLoggedUserDetails = task.getResult();
            taskCompletionSource.setResult(resultLoggedUserDetails);
        });
        return taskCompletionSource.getTask();
    }
}
