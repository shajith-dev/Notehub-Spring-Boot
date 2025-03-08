package com.example.notehub.comment;

import com.example.jooq.generated.tables.records.CommentsRecord;
import org.jooq.DSLContext;
import org.jooq.Record8;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.jooq.generated.Tables.COMMENTS;
import static com.example.jooq.generated.Tables.USERS;

@Repository
public class CommentDAO {

    @Autowired
    private DSLContext dslContext;

    public Comment createComment(Comment comment){
        CommentsRecord commentsRecord = dslContext.insertInto(COMMENTS)
                .set(COMMENTS.CONTENT,comment.getContent())
                .set(COMMENTS.CREATED_BY,comment.getCreatedBy())
                .set(COMMENTS.NOTE_ID,comment.getNoteId())
                .set(COMMENTS.PARENT_ID,comment.getParentId())
                .returning().fetchOne();
        if(commentsRecord != null){
            comment.setCommentId(commentsRecord.getCommentId());
        }
        return comment;
    }

    public void deleteComment(Long noteId,Long commentId){
        dslContext.update(COMMENTS)
                .set(COMMENTS.IS_DELETED,true)
                .where(COMMENTS.COMMENT_ID.eq(commentId))
                .and(COMMENTS.NOTE_ID.eq(noteId))
                .and(COMMENTS.IS_DELETED.eq(false))
                .execute();
        dslContext.update(COMMENTS)
                .set(COMMENTS.IS_DELETED,true)
                .where(COMMENTS.PARENT_ID.eq(commentId))
                .and(COMMENTS.NOTE_ID.eq(noteId))
                .and(COMMENTS.IS_DELETED.eq(false))
                .execute();
    }

    public List<Comment> getAllComments(Long noteId) {
        return dslContext
                .select(
                        COMMENTS.COMMENT_ID,
                        COMMENTS.NOTE_ID,
                        COMMENTS.CONTENT,
                        COMMENTS.PARENT_ID,
                        COMMENTS.CREATED_BY,
                        USERS.USERNAME,
                        USERS.URL,
                        COMMENTS.CREATED_AT
                )
                .from(COMMENTS)
                .join(USERS)
                .on(COMMENTS.CREATED_BY.eq(USERS.USER_ID))
                .where(COMMENTS.NOTE_ID.eq(noteId))
                .and(COMMENTS.IS_DELETED.isFalse())
                .orderBy(COMMENTS.CREATED_AT)
                .fetch(record -> {
                    Comment comment = new Comment();
                    comment.setCommentId(record.get(COMMENTS.COMMENT_ID));
                    comment.setNoteId(record.get(COMMENTS.NOTE_ID));
                    comment.setContent(record.get(COMMENTS.CONTENT));
                    comment.setParentId(record.get(COMMENTS.PARENT_ID));
                    comment.setCreatedBy(record.get(COMMENTS.CREATED_BY));
                    comment.setAuthor(record.get(USERS.USERNAME));
                    comment.setAuthorPfp(record.get(USERS.URL));
                    comment.setCreatedAt(record.get(COMMENTS.CREATED_AT));
                    return comment;
                });
    }

    public Comment editComment(Long noteId,Long commentId,String content){
        return dslContext.update(COMMENTS)
                .set(COMMENTS.CONTENT,content)
                .where(COMMENTS.NOTE_ID.eq(noteId))
                .and(COMMENTS.COMMENT_ID.eq(commentId))
                .and(COMMENTS.IS_DELETED.eq(false))
                .returning().fetchOneInto(Comment.class);
    }

}
