package com.example.demo;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "teachers")
public class PersonListWrapper {

    private List<Teachers> teachers;

    @XmlElement(name = "teacher")
    public List<Teachers> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teachers> teachers) {
        this.teachers = teachers;
    }
}
