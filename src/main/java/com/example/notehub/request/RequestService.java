package com.example.notehub.request;

import com.example.notehub.dto.PagedResult;
import com.example.notehub.note.Note;
import com.example.notehub.note.NoteService;
import com.example.notehub.users.User;
import com.example.notehub.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private NoteService noteService;

    @Autowired
    private UserService userService;

    public PagedResult<Request> getAllRequests(List<Long> subjectIds,Long page){
        return requestDAO.getAllRequests(subjectIds,page);
    }

    public Request createRequest(Request request){
        return requestDAO.createRequest(request);
    }

    public void deleteRequest(Long requestId){
        requestDAO.deleteRequest(requestId);
    }

    @Transactional
    public void resolveRequest(Long requestId, Note note, MultipartFile file){
        note = noteService.createNote(note,file);
        requestDAO.resolveRequest(requestId,note.getCreatedBy());
    }

    public PagedResult<Request> getMyRequests(Long page){
        String userName = userService.getCurrentUsername();
        User user = userService.getUserByUserName(userName);
        return requestDAO.getMyRequests(user.getUserId(),page);
    }
}
