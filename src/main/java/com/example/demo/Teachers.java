package com.example.demo;

public class Teachers {
    String faculties, chairs, teachers, posts;

    public Teachers(String faculties, String chairs, String teachers, String posts) {
        this.faculties = faculties;
        this.chairs = chairs;
        this.teachers = teachers;
        this.posts = posts;
    }

    public String getFaculties() {
        return faculties;
    }

    public String getChairs() {
        return chairs;
    }

    public String getTeachers() {
        return teachers;
    }

    public String getPosts() {
        return posts;
    }

    public void setFaculties(String faculties) {
        this.faculties = faculties;
    }

    public void setChairs(String chairs) {
        this.chairs = chairs;
    }

    public void setTeachers(String teachers) {
        this.teachers = teachers;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }
}
