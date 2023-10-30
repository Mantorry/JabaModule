package com.example.demo;

public class Teachers {
    String id, faculties, chairs, fio, posts;

    public Teachers(String id, String faculties, String chairs, String fio, String posts) {
        this.id = id;
        this.faculties = faculties;
        this.chairs = chairs;
        this.fio = fio;
        this.posts = posts;
    }

    public Teachers() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFaculties() {
        return faculties;
    }

    public void setFaculties(String faculties) {
        this.faculties = faculties;
    }

    public String getChairs() {
        return chairs;
    }

    public void setChairs(String chairs) {
        this.chairs = chairs;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }
}
