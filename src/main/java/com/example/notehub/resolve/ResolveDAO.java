package com.example.notehub.resolve;

import com.example.jooq.generated.tables.records.ResolvesRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.jooq.generated.Tables.RESOLVES;


public class ResolveDAO {

    @Autowired
    private DSLContext dslContext;

    public Resolve createResolve(Resolve resolve){
        ResolvesRecord record = dslContext.insertInto(RESOLVES)
                .set(RESOLVES.SUBMITTED_BY,resolve.getSubmittedBy())
                .set(RESOLVES.REQUEST_ID,resolve.getRequestId())
                .set(RESOLVES.URL,resolve.getUrl())
                .returning().fetchOne();
        if(record != null){
            resolve.setResolveId(resolve.getResolveId());
        }
        return resolve;
    }

    public Resolve getResolve(Long resolveId){
        return dslContext.selectFrom(RESOLVES)
                .where(RESOLVES.RESOLVE_ID.eq(resolveId))
                .and(RESOLVES.IS_DELETED.eq(false))
                .fetchOneInto(Resolve.class);
    }

    public void deleteResolve(Long resolveId){
        dslContext.update(RESOLVES)
                .set(RESOLVES.IS_DELETED,true)
                .where(RESOLVES.IS_DELETED.eq(false))
                .and(RESOLVES.RESOLVE_ID.eq(resolveId))
                .execute();
    }

    public void approveResolve(Long resolveId){
         dslContext.update(RESOLVES)
                .set(RESOLVES.APPROVED,true)
                .where(RESOLVES.IS_DELETED.eq(false))
                .and(RESOLVES.RESOLVE_ID.eq(resolveId))
                .execute();
    }
}
