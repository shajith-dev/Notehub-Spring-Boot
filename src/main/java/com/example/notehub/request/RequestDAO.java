package com.example.notehub.request;

import com.example.jooq.generated.tables.records.RequestsRecord;
import com.example.notehub.dto.PagedResult;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jooq.generated.Tables.REQUESTS;
import static com.example.jooq.generated.Tables.USERS;

@Repository
public class RequestDAO {

    private static final Long OFFSET = 10L;
    private static final Long LIMIT = 10L;

    @Autowired
    private DSLContext dslContext;

    public PagedResult<Request> getAllRequests(List<Long> subjectIds, Long page) {
        Condition subjectCondition = !(subjectIds == null || subjectIds.isEmpty()) ?
                REQUESTS.SUBJECT_ID.in(subjectIds) : DSL.trueCondition();

        List<Request> requests = dslContext.select(
                        REQUESTS.REQUEST_ID,
                        REQUESTS.TITLE,
                        REQUESTS.SUBJECT_ID,
                        REQUESTS.REQUESTED_BY,
                        REQUESTS.CREATED_AT,
                        REQUESTS.RESOLVED,
                        REQUESTS.RESOLVED_BY,
                        REQUESTS.IS_DELETED,
                        REQUESTS.DESCRIPTION,
                        USERS.USERNAME
                )
                .from(REQUESTS)
                .join(USERS)
                .on(REQUESTS.REQUESTED_BY.eq(USERS.USER_ID))
                .where(subjectCondition)
                .and(REQUESTS.IS_DELETED.eq(false))
                .and(REQUESTS.RESOLVED.eq(false))
                .orderBy(REQUESTS.REQUEST_ID)
                .limit(LIMIT + 1)
                .offset(OFFSET * page)
                .fetch().map(record -> new Request(
                        record.get(REQUESTS.REQUEST_ID),
                        record.get(REQUESTS.TITLE),
                        record.get(REQUESTS.SUBJECT_ID),
                        record.get(REQUESTS.REQUESTED_BY),
                        record.get(REQUESTS.CREATED_AT),
                        record.get(REQUESTS.RESOLVED),
                        record.get(REQUESTS.RESOLVED_BY),
                        record.get(REQUESTS.IS_DELETED),
                        record.get(REQUESTS.DESCRIPTION),
                        record.get(USERS.USERNAME)
                ));

        boolean hasMore = requests.size() > LIMIT;

        if (hasMore) {
            requests.remove(requests.size()-1); // Better than removeLast() for List implementations
        }

        return new PagedResult<>(requests, hasMore);
    }

    public Request createRequest(Request request){
        RequestsRecord record = dslContext.insertInto(REQUESTS)
                .set(REQUESTS.REQUESTED_BY, request.getRequestedBy())
                .set(REQUESTS.SUBJECT_ID, request.getSubjectId())
                .set(REQUESTS.DESCRIPTION,request.getDescription())
                .set(REQUESTS.TITLE,request.getTitle())
                .returning().fetchOne();
        if(record != null) {
            request.setRequestedBy(record.getRequestId());
        }
        return request;
    }

    public void deleteRequest(Long requestId){
        dslContext.update(REQUESTS)
                .set(REQUESTS.IS_DELETED,true)
                .where(REQUESTS.REQUEST_ID.eq(requestId))
                .and(REQUESTS.IS_DELETED.eq(false))
                .execute();
    }

    public void resolveRequest(Long requestId,Long resolverId){
        dslContext.update(REQUESTS)
                .set(REQUESTS.RESOLVED,true)
                .set(REQUESTS.RESOLVED_BY,resolverId)
                .where(REQUESTS.IS_DELETED.eq(false))
                .and(REQUESTS.REQUEST_ID.eq(requestId))
                .and(REQUESTS.RESOLVED.eq(false))
                .execute();
    }

    public Request getRequestById(Long requestId){
        return dslContext.selectFrom(REQUESTS)
                .where(REQUESTS.REQUEST_ID.eq(requestId))
                .and(REQUESTS.IS_DELETED.eq(false))
                .fetchOneInto(Request.class);
    }

    public PagedResult<Request> getMyRequests(Long userId,Long page) {

        List<Request> requests = dslContext.select(
                        REQUESTS.REQUEST_ID,
                        REQUESTS.TITLE,
                        REQUESTS.SUBJECT_ID,
                        REQUESTS.REQUESTED_BY,
                        REQUESTS.CREATED_AT,
                        REQUESTS.RESOLVED,
                        REQUESTS.RESOLVED_BY,
                        REQUESTS.IS_DELETED,
                        REQUESTS.DESCRIPTION,
                        USERS.USERNAME
                )
                .from(REQUESTS)
                .join(USERS)
                .on(REQUESTS.REQUESTED_BY.eq(USERS.USER_ID))
                .where(REQUESTS.REQUESTED_BY.eq(userId))
                .and(REQUESTS.IS_DELETED.eq(false))
                .and(REQUESTS.RESOLVED.eq(false))
                .orderBy(REQUESTS.REQUEST_ID)
                .limit(LIMIT + 1)
                .offset(OFFSET * page)
                .fetch().map(record -> new Request(
                        record.get(REQUESTS.REQUEST_ID),
                        record.get(REQUESTS.TITLE),
                        record.get(REQUESTS.SUBJECT_ID),
                        record.get(REQUESTS.REQUESTED_BY),
                        record.get(REQUESTS.CREATED_AT),
                        record.get(REQUESTS.RESOLVED),
                        record.get(REQUESTS.RESOLVED_BY),
                        record.get(REQUESTS.IS_DELETED),
                        record.get(REQUESTS.DESCRIPTION),
                        record.get(USERS.USERNAME)
                ));

        boolean hasMore = requests.size() > LIMIT;

        if (hasMore) {
            requests.remove(requests.size()-1); // Better than removeLast() for List implementations
        }

        return new PagedResult<>(requests, hasMore);
    }

}
