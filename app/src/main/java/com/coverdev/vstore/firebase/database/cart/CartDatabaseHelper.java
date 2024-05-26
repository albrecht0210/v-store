package com.coverdev.vstore.firebase.database.cart;

import com.coverdev.vstore.model.Cart;
import com.coverdev.vstore.model.Merchandise;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartDatabaseHelper {
    private FirebaseFirestore db;
    public CartDatabaseHelper() {
        this.db = FirebaseFirestore.getInstance();
    }

    public Task<Boolean> createCart(Cart newCart) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        Map<String, Object> cartMap = newCart.toDocument();

        db.collection("carts")
                .add(cartMap)
                .addOnCompleteListener(task -> {
                    taskCompletionSource.setResult(task.isSuccessful());
                });
        return taskCompletionSource.getTask();
    }

    public Task<List<Cart>> listCarts(String loggedId) {
        final TaskCompletionSource<List<Cart>> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("carts")
                .whereEqualTo("userId", loggedId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot result = task.getResult();
                        if (!result.isEmpty()) {
                            List<Cart> carts = new ArrayList<>();
                            for (QueryDocumentSnapshot document : result) {
                                carts.add(Cart.toCart(document.getId(), document.getData()));
                            }
                            taskCompletionSource.setResult(carts);
                        } else {
                            taskCompletionSource.setResult(null);
                        }
                    } else {
                        taskCompletionSource.setResult(null);
                    }
                });
        return taskCompletionSource.getTask();
    }

    public Task<Boolean> addMerchandiseToCart(String id, Merchandise item) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        Map<String, Object> merchandiseMap = item.toDocument();

        db.collection("carts").document(id)
                .update(merchandiseMap)
                .addOnCompleteListener(task -> {
                    taskCompletionSource.setResult(task.isSuccessful());
                });
        return taskCompletionSource.getTask();
    }

    public Task<Boolean> removeMerchandiseToCart(String id, String merchandiseId) {
        final TaskCompletionSource<Boolean> taskCompletionSource = new TaskCompletionSource<>();

        db.collection("carts").document(id)
                .update("merchandises." + merchandiseId, FieldValue.delete())
                .addOnCompleteListener(task -> {
                    taskCompletionSource.setResult(task.isSuccessful());
                });
        return taskCompletionSource.getTask();
    }
}
