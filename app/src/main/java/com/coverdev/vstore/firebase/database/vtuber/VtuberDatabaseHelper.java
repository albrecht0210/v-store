package com.coverdev.vstore.firebase.database.vtuber;

import com.coverdev.vstore.model.User;
import com.coverdev.vstore.model.Vtuber;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VtuberDatabaseHelper {
    private FirebaseFirestore db;
    public VtuberDatabaseHelper() {
        this.db = FirebaseFirestore.getInstance();
    }

    public Task<Boolean> createVtuber(Vtuber newVtuber) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        Map<String, Object> vtuberMap = newVtuber.toDocument();

        db.collection("vtubers")
                .add(vtuberMap)
                .addOnCompleteListener(task -> {
                    taskCompletionSource.setResult(task.isSuccessful());
                });
        return taskCompletionSource.getTask();
    }

    public Task<Vtuber> retrieveVtuberByID(String id) {
        final TaskCompletionSource<Vtuber> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("vtubers").document(id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        taskCompletionSource.setResult(Vtuber.toVtuber(document.getId(), document.getData()));
                    } else {
                        taskCompletionSource.setResult(null);
                    }
                });
        return taskCompletionSource.getTask();
    }

    public Task<Vtuber> retrieveVtuberByName(String name) {
        final TaskCompletionSource<Vtuber> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("vtubers")
            .whereEqualTo("name", name)
            .limit(1)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot result = task.getResult();
                    if (!result.isEmpty()) {
                        for (QueryDocumentSnapshot document : result) {
                            taskCompletionSource.setResult(Vtuber.toVtuber(document.getId(), document.getData()));
                        }
                    } else {
                        taskCompletionSource.setResult(null);
                    }
                } else {
                    taskCompletionSource.setResult(null);
                }
            });
        return taskCompletionSource.getTask();
    }

    public Task<List<Vtuber>> listVtubers() {
        final TaskCompletionSource<List<Vtuber>> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("vtubers")
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot result = task.getResult();
                    if (!result.isEmpty()) {
                        List<Vtuber> vtubers = new ArrayList<>();
                        for (QueryDocumentSnapshot document : result) {
                            vtubers.add(Vtuber.toVtuber(document.getId(), document.getData()));
                        }
                        taskCompletionSource.setResult(vtubers);
                    } else {
                        taskCompletionSource.setResult(null);
                    }
                } else {
                    taskCompletionSource.setResult(null);
                }
            });
        return taskCompletionSource.getTask();
    }

    public Task<List<Vtuber>> listVtubersByBranch(String branch) {
        return listVtubersByField("branch", branch);
    }

    public Task<List<Vtuber>> listVtubersByGeneration(String generation) {
        return listVtubersByField("generation", generation);
    }

    private Task<List<Vtuber>> listVtubersByField(String field, Object value) {
        final TaskCompletionSource<List<Vtuber>> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("vtubers")
            .whereEqualTo(field, value)
            .get()
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    QuerySnapshot result = task.getResult();
                    if (!result.isEmpty()) {
                        List<Vtuber> vtubers = new ArrayList<>();
                        for (QueryDocumentSnapshot document : result) {
                            vtubers.add(Vtuber.toVtuber(document.getId(), document.getData()));
                        }
                        taskCompletionSource.setResult(vtubers);
                    } else {
                        taskCompletionSource.setResult(null);
                    }
                } else {
                    taskCompletionSource.setResult(null);
                }
            });
        return taskCompletionSource.getTask();
    }


}
