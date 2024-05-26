package com.coverdev.vstore.firebase.database.merchandise;

import com.coverdev.vstore.model.Merchandise;
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

public class MerchandiseDatabaseHelper {
    private FirebaseFirestore db;
    public MerchandiseDatabaseHelper() {
        this.db = FirebaseFirestore.getInstance();
    }

    public Task<Boolean> createMerchandise(Merchandise newMerchandise) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        Map<String, Object> merchandiseMap = newMerchandise.toDocument();

        db.collection("merchandises")
                .add(merchandiseMap)
                .addOnCompleteListener(task -> {
                    taskCompletionSource.setResult(task.isSuccessful());
                });
        return taskCompletionSource.getTask();
    }

    public Task<Merchandise> retrieveMerchandiseByID(String id) {
        final TaskCompletionSource<Merchandise> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("merchandises").document(id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        taskCompletionSource.setResult(Merchandise.toMerchandise(document.getId(), document.getData()));
                    } else {
                        taskCompletionSource.setResult(null);
                    }
                });
        return taskCompletionSource.getTask();
    }

    public Task<Merchandise> retrieveMerchandiseByName(String name) {
        final TaskCompletionSource<Merchandise> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("merchandises")
                .whereEqualTo("name", name)
                .limit(1)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        if (!result.isEmpty()) {
                            for (QueryDocumentSnapshot document : result) {
                                taskCompletionSource.setResult(Merchandise.toMerchandise(document.getId(), document.getData()));
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

    public Task<List<Merchandise>> listMerchandises() {
        final TaskCompletionSource<List<Merchandise>> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("merchandises")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        if (!result.isEmpty()) {
                            List<Merchandise> merchandises = new ArrayList<>();
                            for (QueryDocumentSnapshot document : result) {
                                merchandises.add(Merchandise.toMerchandise(document.getId(), document.getData()));
                            }
                            taskCompletionSource.setResult(merchandises);
                        } else {
                            taskCompletionSource.setResult(null);
                        }
                    } else {
                        taskCompletionSource.setResult(null);
                    }
                });
        return taskCompletionSource.getTask();
    }

    public Task<List<Merchandise>> listMerchandisesByCategory(String category) {
        return listMerchandisesByField("category", category);
    }

    public Task<List<Merchandise>> listMerchandisesByAvailability(Boolean availability) {
        return listMerchandisesByField("availability", availability);
    }

    private Task<List<Merchandise>> listMerchandisesByField(String field, Object value) {
        final TaskCompletionSource<List<Merchandise>> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("merchandises")
                .whereEqualTo(field, value)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        if (!result.isEmpty()) {
                            List<Merchandise> merchandises = new ArrayList<>();
                            for (QueryDocumentSnapshot document : result) {
                                merchandises.add(Merchandise.toMerchandise(document.getId(), document.getData()));
                            }
                            taskCompletionSource.setResult(merchandises);
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
