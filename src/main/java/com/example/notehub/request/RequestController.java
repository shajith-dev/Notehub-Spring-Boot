package com.example.notehub.request;

import com.example.notehub.dto.PagedResult;
import com.example.notehub.note.Note;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public PagedResult<Request> getAllRequests(@RequestParam(value = "sids", required = false) List<Long> subjectIds, @RequestParam(value = "page",defaultValue = "0") Long page){
        return requestService.getAllRequests(subjectIds,page);
    }

    @PostMapping
    public Request createRequest(@RequestBody Request request){
        return requestService.createRequest(request);
    }

    @DeleteMapping(path = "/{requestId}")
    public void deleteRequest(@PathVariable Long requestId){
        requestService.deleteRequest(requestId);
    }

    @PostMapping(path = "/{requestId}/resolve")
    public void resolveRequest(@PathVariable Long requestId, @RequestPart String noteJson, @RequestPart("file")MultipartFile file) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Note note = mapper.readValue(noteJson,Note.class);
        requestService.resolveRequest(requestId,note,file);
    }

    @GetMapping(path = "/me")
    public PagedResult<Request> getMyRequests(@RequestParam(defaultValue = "0") Long page){
        return requestService.getMyRequests(page);
    }
}
