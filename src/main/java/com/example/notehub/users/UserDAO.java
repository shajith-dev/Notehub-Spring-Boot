package com.example.notehub.users;

import com.example.jooq.generated.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static com.example.jooq.generated.Tables.USERS;

import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private DSLContext dslContext;

    public User getUser(Long userId){
        return dslContext.select(USERS.USER_ID,USERS.CREATED_AT,USERS.USERNAME,USERS.URL,USERS.DESCRIPTION,USERS.EMAIL_ID,USERS)
                .from(USERS)
                .where(USERS.USER_ID.eq(Math.toIntExact(userId)))
                .fetchOneInto(User.class);
    }

    public List<User> getAllUsers(){
        return dslContext.selectFrom(USERS).fetchInto(User.class);
    }

    public User createUser(User user){
        UsersRecord record = dslContext.insertInto(USERS)
                .set(USERS.USERNAME,user.getUserName())
                .set(USERS.EMAIL_ID,user.getEmailId())
                .set(USERS.PASSWORD,user.getPassword())
                .set(USERS.DESCRIPTION,user.getDescription())
                .set(USERS.URL,user.getUrl())
                .returning().fetchOne();

        if(record != null){
            user.setUserId(record.getUserId().longValue());
            user.setCreatedAt(record.getCreatedAt());
        }
        return user;
    }

    public User getUserByUserName(String userName) {
        return dslContext.selectFrom(USERS)
                .where(USERS.USERNAME.eq(userName))
                .and(USERS.IS_DELETED.eq(false))
                .fetchOneInto(User.class);
    }
}