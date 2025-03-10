package com.example.notehub.note;

import com.example.jooq.generated.tables.records.NotesRecord;
import com.example.notehub.dto.PagedResult;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jooq.generated.Tables.*;

@Repository
public class NoteDAO {

    private static final Long OFFSET = 10L;
    private static final Long LIMIT = 10L;

    @Autowired
    private DSLContext dslContext;

    public Note getNote(Long noteId){
        return dslContext.selectFrom(NOTES)
                .where(NOTES.NOTE_ID.eq(noteId))
                .and(NOTES.IS_DELETED.eq(false))
                .fetchOneInto(Note.class);
    }

    public Note createNote(Note note){
        NotesRecord notesRecord = dslContext.insertInto(NOTES)
                .set(NOTES.TITLE,note.getTitle())
                .set(NOTES.CREATED_BY,note.getCreatedBy())
                .set(NOTES.SUBJECT_ID,note.getSubjectId())
                .set(NOTES.SUBJECT_ID,note.getSubjectId())
                .set(NOTES.URL,note.getUrl())
                .returning().fetchOne();

        if(notesRecord != null) {
            note.setNoteId(notesRecord.getNoteId());
        }
        return note;
    }

    public void deleteNote(Long noteId){
        dslContext.update(NOTES)
                .set(NOTES.IS_DELETED,true)
                .where(NOTES.NOTE_ID.eq(noteId))
                .and(NOTES.IS_DELETED.eq(false))
                .execute();
    }

    public PagedResult<Note> searchNotes(String query,List<Long> subjectIds, Long page){
        Condition subjectCondition = !(subjectIds == null || subjectIds.isEmpty()) ? NOTES.SUBJECT_ID.in(subjectIds) : DSL.trueCondition();
        Condition queryCondition = query.isEmpty() ? DSL.trueCondition() : NOTES.TITLE.containsIgnoreCase(query);

        List<Note> notes = dslContext.select(
                    NOTES.NOTE_ID,
                    NOTES.TITLE,
                    NOTES.CREATED_BY,
                    NOTES.URL,
                    NOTES.SUBJECT_ID,
                    SUBJECTS.NAME,
                    USERS.USERNAME
                )
                .from(NOTES)
                .join(SUBJECTS)
                .on(NOTES.SUBJECT_ID.eq(SUBJECTS.SUBJECT_ID))
                .join(USERS)
                .on(NOTES.CREATED_BY.eq(USERS.USER_ID))
                .where(NOTES.IS_DELETED.eq(false))
                .and(queryCondition)
                .and(subjectCondition)
                .orderBy(NOTES.NOTE_ID)
                .limit(LIMIT + 1)
                .offset(OFFSET * page)
                .fetch(record -> {
                    Note note = new Note();
                    note.setTitle(record.get(NOTES.TITLE));
                    note.setNoteId(record.get(NOTES.NOTE_ID));
                    note.setCreatedBy(record.get(NOTES.CREATED_BY));
                    note.setAuthor(record.get(USERS.USERNAME));
                    note.setUrl(record.get(NOTES.URL));
                    note.setSubjectName(record.get(SUBJECTS.NAME));
                    note.setSubjectId(record.get(NOTES.SUBJECT_ID));
                    return note;
                });

        boolean hasMore = notes.size() > LIMIT;

        if (hasMore) {
            notes.remove(notes.size()-1);
        }

        return new PagedResult<>(notes, hasMore);

    }

    public PagedResult<Note> getNotes(Long userId,Long page){
        List<Note> notes = dslContext.select(
                        NOTES.NOTE_ID,
                        NOTES.TITLE,
                        NOTES.CREATED_BY,
                        NOTES.URL,
                        NOTES.SUBJECT_ID,
                        SUBJECTS.NAME,
                        USERS.USERNAME
                )
                .from(NOTES)
                .join(SUBJECTS)
                .on(NOTES.SUBJECT_ID.eq(SUBJECTS.SUBJECT_ID))
                .join(USERS)
                .on(NOTES.CREATED_BY.eq(USERS.USER_ID))
                .where(NOTES.IS_DELETED.eq(false))
                .orderBy(NOTES.NOTE_ID)
                .limit(LIMIT + 1)
                .offset(OFFSET * page)
                .fetch(record -> {
                    Note note = new Note();
                    note.setTitle(record.get(NOTES.TITLE));
                    note.setNoteId(record.get(NOTES.NOTE_ID));
                    note.setCreatedBy(record.get(NOTES.CREATED_BY));
                    note.setAuthor(record.get(USERS.USERNAME));
                    note.setUrl(record.get(NOTES.URL));
                    note.setSubjectName(record.get(SUBJECTS.NAME));
                    note.setSubjectId(record.get(NOTES.SUBJECT_ID));
                    return note;
                });

        boolean hasMore = notes.size() > LIMIT;

        if (hasMore) {
            notes.remove(notes.size() - 1);
        }

        return new PagedResult<>(notes, hasMore);
    }

}
