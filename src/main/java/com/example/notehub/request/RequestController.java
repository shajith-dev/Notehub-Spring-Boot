package com.example.notehub.request;

import com.example.notehub.dto.PagedResult;
import com.example.notehub.resolve.Resolve;
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
    public void resolveRequest(@PathVariable Long requestId,@RequestBody Resolve resolve){
        requestService.resolveRequest(requestId,resolve);
    }
}
