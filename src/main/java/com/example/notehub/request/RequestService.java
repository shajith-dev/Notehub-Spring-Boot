package com.example.notehub.request;

import com.example.notehub.dto.PagedResult;
import com.example.notehub.note.Note;
import com.example.notehub.note.NoteService;
import com.example.notehub.resolve.Resolve;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private NoteService noteService;

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
    public void resolveRequest(Long requestId, Resolve resolve){
        Request request = requestDAO.getRequestById(requestId);
        requestDAO.resolveRequest(resolve);
        Note note = new Note();
        note.setTitle(resolve.getTitle());
        note.setUrl(resolve.getUrl());
        note.setSubjectId(request.getSubjectId());
        note.setCreatedBy(resolve.getSubmittedBy());
        noteService.createNote(note);
    }
}
