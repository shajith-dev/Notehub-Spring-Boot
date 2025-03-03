package com.example.notehub.request;

import com.example.notehub.dto.PagedResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class RequestController {

    @Autowired
    private RequestService requestService;

    @GetMapping
    public PagedResult<Request> getAllRequests(@RequestParam(value = "sids", required = false) List<Long> subjectIds, @RequestParam(value = "page",defaultValue = "0") Long page){
        return requestService.getAllNotes(subjectIds,page);
    }

    @PostMapping
    public Request createRequest(@RequestBody Request request){
        return requestService.createRequest(request);
    }

    @DeleteMapping(path = "/{requestId}")
    public void deleteRequest(@PathVariable Long requestId){
        requestService.deleteRequest(requestId);
    }
}
