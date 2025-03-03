package com.example.notehub.request;

import com.example.notehub.dto.PagedResult;
import com.example.notehub.note.Note;
import com.example.notehub.note.NoteService;
import com.example.notehub.resolve.Resolve;
import com.example.notehub.resolve.ResolveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestService {

    @Autowired
    private RequestDAO requestDAO;

    @Autowired
    private ResolveService resolveService;

    @Autowired
    private NoteService noteService;

    public PagedResult<Request> getAllNotes(List<Long> subjectIds,Long page){
        return requestDAO.getAllRequests(subjectIds,page);
    }

    public Request createRequest(Request request){
        return requestDAO.createRequest(request);
    }

    public void deleteRequest(Long requestId){
        requestDAO.deleteRequest(requestId);
    }

    public void resolveRequest(Resolve resolve){
        requestDAO.resolveRequest(resolve);
        resolveService.approveResolve(resolve.getResolveId());
        Note note = new Note();
        note.setUrl(resolve.getUrl());
        noteService.createNote(note);
    }
}
