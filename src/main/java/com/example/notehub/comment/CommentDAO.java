package com.example.notehub.comment;

import com.example.jooq.generated.tables.records.CommentsRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jooq.generated.Tables.COMMENTS;

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
        return dslContext.selectFrom(COMMENTS)
                .where(COMMENTS.NOTE_ID.eq(noteId))
                .and(COMMENTS.IS_DELETED.eq(false))
                .fetchInto(Comment.class);
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
