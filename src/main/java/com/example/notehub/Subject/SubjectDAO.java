package com.example.notehub.Subject;

import com.example.jooq.generated.tables.records.SubjectsRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static com.example.jooq.generated.Tables.SUBJECTS;

import java.util.List;

@Repository
public class SubjectDAO {

    @Autowired
    private DSLContext dslContext;

    public List<Subject> getAllSubjects(){
        return dslContext.selectFrom(SUBJECTS)
                .fetchInto(Subject.class);
    }

    public Subject createSubject(Subject subject){
        SubjectsRecord record = dslContext.insertInto(SUBJECTS)
                .set(SUBJECTS.NAME,subject.getName())
                .returning().fetchOne();
        if(record != null){
            subject.setSubjectId(record.getSubjectId());
        }
        return subject;
    }
}
