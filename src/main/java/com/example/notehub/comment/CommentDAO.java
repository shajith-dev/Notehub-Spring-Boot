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

    public void deleteComment(Long commentId){
        dslContext.update(COMMENTS)
                .set(COMMENTS.IS_DELETED,true)
                .where(COMMENTS.COMMENT_ID.eq(commentId))
                .and(COMMENTS.IS_DELETED.eq(false))
                .execute();
    }

    public List<Comment> getAllComments(Long noteId){
         List<Record8<Long, Long, String, Long, Long, String, String, LocalDateTime>> commentRecords = dslContext
                .select(COMMENTS.COMMENT_ID,COMMENTS.NOTE_ID,COMMENTS.CONTENT,COMMENTS.PARENT_ID,COMMENTS.CREATED_BY,USERS.USERNAME,USERS.URL,COMMENTS.CREATED_AT)
                .from(COMMENTS)
                .join(USERS)
                .on(COMMENTS.CREATED_BY.eq(USERS.USER_ID))
                .where(COMMENTS.NOTE_ID.eq(noteId))
                .and(COMMENTS.IS_DELETED.isFalse())
                .orderBy(COMMENTS.CREATED_AT)
                .fetch();
        return commentRecords.stream().map(record -> {
            Comment comment = new Comment();
            comment.setCommentId(record.component1());
            comment.setNoteId(record.component2());
            comment.setContent(record.component3());
            comment.setParentId(record.component4());
            comment.setCreatedBy(record.component5());
            comment.setAuthor(record.component6());
            comment.setAuthorPfp(record.component7());
            comment.setCreatedAt(record.component8());
            return comment;
        }).toList();
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
