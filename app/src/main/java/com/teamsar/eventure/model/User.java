package com.teamsar.eventure.model;

public class User {

    /*
    Initialized the fields for the model user class;
     */

    private String name;
    private String imageUri;
    private String id;
    private String email;
    private String bio;


    //Empty constructor for null data
    public User() {
    }


    //parameterised constructor for data
    public User(String name, String imageUri, String id, String email, String bio) {
        this.name = name;
        this.imageUri = imageUri;
        this.id = id;
        this.email = email;
        this.bio = bio;
    }

    /*
    Getters and setters for every field to provide abstraction
     */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
