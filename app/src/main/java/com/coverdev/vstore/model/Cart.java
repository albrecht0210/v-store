package com.coverdev.vstore.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private String id;
    private String user;
    private List<Merchandise> merchandises;
    private Boolean status;
    private Integer totalPrice;

    public Cart() {

    }

    public Cart(String id, String user, List<Merchandise> merchandises, Boolean status, Integer totalPrice) {
        this.id = id;
        this.user = user;
        this.merchandises = merchandises;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getStatus() {
        return status ? "pending" : "done";
    }

    public List<Merchandise> getMerchandises() {
        return merchandises;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setMerchandises(List<Merchandise> merchandises) {
        this.merchandises = merchandises;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Map<String, Object> toDocument() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> cart = new HashMap<>();
        List<DocumentReference> merchandisesDocRef = new ArrayList<>();
        for (Merchandise merchandise: this.merchandises) {
            merchandisesDocRef.add(db.collection("merchandises").document(merchandise.getId()));
        }
        cart.put("userId", this.user);
        cart.put("merchandises", merchandisesDocRef);
        cart.put("status", this.status);
        cart.put("totalPrice", this.totalPrice);

        return cart;
    }

    public static Cart toCart(String id, Map<String, Object> cartMap) {
        String userId = cartMap.get("userId").toString();

        List<DocumentReference> merchandisesDocRef = (List<DocumentReference>) cartMap.get("merchandises");

        final List<Merchandise> merchandisesList = new ArrayList<>();

        for (DocumentReference merchandiseDocRef: merchandisesDocRef) {
            merchandiseDocRef
                    .get()
                    .addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   DocumentSnapshot document = task.getResult();
                   Merchandise item = Merchandise.toMerchandise(document.getId(), document.getData());
                   merchandisesList.add(item);
               }
            });
        }

//        List<> merchandiseMaps = (List<Map<String, Object>>) cartMap.get("merchandises");
//        List<Merchandise> merchandises = merchandiseMaps.stream()
//                .map(Merchandise::toMerchandise)  // Assuming you have a toMerchandise method in the Merchandise class
//                .collect(Collectors.toList());

        String status = cartMap.get("status").toString();
        Integer totalPrice = Integer.parseInt(cartMap.get("totalPrice").toString());

        return new Cart(id, userId, merchandisesList, status.equals("pending"), totalPrice);
    }
}
