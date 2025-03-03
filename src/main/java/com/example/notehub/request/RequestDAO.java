package com.example.notehub.request;

import com.example.jooq.generated.tables.records.RequestsRecord;
import com.example.notehub.dto.PagedResult;
import com.example.notehub.resolve.Resolve;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.jooq.generated.Tables.REQUESTS;

@Repository
public class RequestDAO {

    private static final Long OFFSET = 2L;
    private static final Long LIMIT = 2L;

    @Autowired
    private DSLContext dslContext;

    public PagedResult<Request> getAllRequests(List<Long> subjectIds, Long page) {
        Condition subjectCondition = !(subjectIds == null || subjectIds.isEmpty()) ? REQUESTS.SUBJECT_ID.in(subjectIds) : DSL.trueCondition();

        List<Request> requests = dslContext.selectFrom(REQUESTS)
                .where(subjectCondition)
                .and(REQUESTS.IS_DELETED.eq(false))
                .and(REQUESTS.RESOLVED.eq(false))
                .orderBy(REQUESTS.REQUEST_ID)
                .limit(LIMIT + 1)
                .offset(OFFSET * page)
                .fetchInto(Request.class);

        boolean hasMore = requests.size() > LIMIT;

        if (hasMore) {
            requests.removeLast();
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

    public void resolveRequest(Resolve resolve){
        dslContext.update(REQUESTS)
                .set(REQUESTS.RESOLVED,true)
                .set(REQUESTS.RESOLVED_BY,resolve.getSubmittedBy())
                .where(REQUESTS.IS_DELETED.eq(false))
                .and(REQUESTS.RESOLVED.eq(false))
                .execute();
    }

}
