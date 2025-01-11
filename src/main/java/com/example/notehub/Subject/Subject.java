package com.example.notehub.subject;

public class Subject {
    private String name;
    private Long subjectId;

    public Subject(String name, Long subjectId) {
        this.name = name;
        this.subjectId = subjectId;
    }

    public Subject(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                ", subjectId=" + subjectId +
                '}';
    }
}
