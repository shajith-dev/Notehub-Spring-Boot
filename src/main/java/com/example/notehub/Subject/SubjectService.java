package com.example.notehub.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectDAO subjectDAO;

    public List<Subject> getAllSubjects(){
        return subjectDAO.getAllSubjects();
    }

    public Subject createSubject(Subject subject){
        return subjectDAO.createSubject(subject);
    }
}
