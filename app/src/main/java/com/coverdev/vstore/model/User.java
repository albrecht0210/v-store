package com.coverdev.vstore.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    public User() {
        id = "";
        firstName = "";
        lastName = "";
        email = "";
        password = "";
    }

    public User(String id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Object> toDocument() {
        Map<String, Object> user = new HashMap<>();
        user.put("first_name", this.firstName);
        user.put("last_name", this.lastName);
        user.put("email", this.email);

        return user;
    }

    public static User toUser(String id, Map<String, Object> userMap) {
        String firstName = userMap.get("first_name").toString();
        String lastName = userMap.get("last_name").toString();
        String email = userMap.get("email").toString();

        return new User(id, firstName, lastName, email, "");
    }

    public static User toUser(FirebaseUser user, String firstName, String lastName) {
        return new User(user.getUid(), firstName, lastName, user.getEmail(), "");
    }
}
