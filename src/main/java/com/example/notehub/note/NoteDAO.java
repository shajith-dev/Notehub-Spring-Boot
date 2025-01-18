package com.example.notehub.note;

import com.example.jooq.generated.tables.records.NotesRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import static com.example.jooq.generated.Tables.NOTES;

@Repository
public class NoteDAO {

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

    public void toggleLike(Long noteId,Long value){
        dslContext.update(NOTES)
                .set(NOTES.LIKES,NOTES.LIKES.plus(value))
                .where(NOTES.NOTE_ID.eq(noteId))
                .and(NOTES.IS_DELETED.eq(false))
                .execute();
    }

    public void deleteNote(Long noteId){
        dslContext.update(NOTES)
                .set(NOTES.IS_DELETED,true)
                .where(NOTES.NOTE_ID.eq(noteId))
                .and(NOTES.IS_DELETED.eq(false))
                .execute();
    }
}
