package com.example.notehub.note;

import com.example.jooq.generated.tables.records.NotesRecord;
import com.example.notehub.dto.PagedResult;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jooq.generated.Tables.NOTES;

@Repository
public class NoteDAO {

    private static final Long OFFSET = 20L;
    private static final Long LIMIT = 20L;

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

    public PagedResult<Note> searchNotes(String query, List<Long> subjectIds, Long page) {

        List<Note> notes = dslContext.selectFrom(NOTES)
                .where(NOTES.TITLE.contains(query))
                .and(NOTES.SUBJECT_ID.in(subjectIds))
                .and(NOTES.IS_DELETED.eq(false))
                .limit(LIMIT + 1)
                .offset(OFFSET * page)
                .fetchInto(Note.class);


        boolean hasMore = notes.size() > LIMIT;

        if (hasMore) {
            notes.removeLast();
        }

        return new PagedResult<>(notes, hasMore);
    }

    public List<Note> getNotes(Long userId){
        return dslContext.selectFrom(NOTES)
                .where(NOTES.CREATED_BY.eq(userId))
                .and(NOTES.IS_DELETED.eq(false))
                .fetchInto(Note.class);
    }

}
