package com.example.notehub.mail;

import com.example.notehub.note.Note;
import com.example.notehub.request.Request;
import com.example.notehub.request.RequestService;
import com.example.notehub.users.User;
import com.example.notehub.users.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class EmailAspect {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @AfterReturning(pointcut = "execution(* com.example.demo.service.RequestService.resolveRequest(..))", returning = "result")
    public void sendEmailAfterResolve(JoinPoint joinPoint, Object result) {
        Object[] args = joinPoint.getArgs();
        Long requestId = (Long) args[0];
        Request request = requestService.getRequestById(requestId);
        Note note = (Note) result;
        User user = userService.getUser(request.getRequestedBy());
        String subject = String.format("Request %d is resolved!!",requestId);
        String body = String.format("%s your request has been resolved , Check it out at https://notehub-react.vercel.app/notes/%d",user.getUserName(),note.getNoteId());
        emailService.sendMail(user.getEmailId(),subject,body);
    }
}
