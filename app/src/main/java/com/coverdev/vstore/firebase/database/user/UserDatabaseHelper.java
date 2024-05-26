package com.coverdev.vstore.firebase.database.user;

import com.coverdev.vstore.model.User;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class UserDatabaseHelper {
    private FirebaseFirestore db;

    public UserDatabaseHelper() {
        this.db = FirebaseFirestore.getInstance();
    }

    public Task<Boolean> addUser(User newUser) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        Map<String, Object> userMap = newUser.toDocument();
        System.out.println("Add User: " + newUser.getId());
        System.out.println("Add User: " + userMap.get("first_name"));
        db.collection("users").document(newUser.getId())
                .set(userMap)
                .addOnCompleteListener(task -> {
                    System.out.println("Is Add User: " + task.isSuccessful());
                    taskCompletionSource.setResult(task.isSuccessful());
                });
        return taskCompletionSource.getTask();
    }

    public Task<User> retrieveProfile(String loggedId) {
        final TaskCompletionSource<User> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("users").document(loggedId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        taskCompletionSource.setResult(User.toUser(document.getId(), document.getData()));
                    } else {
                        taskCompletionSource.setResult(null);
                    }
                });
        return taskCompletionSource.getTask();
    }

}
