package com.example.notehub.resolve;

import com.example.notehub.request.RequestService;
import org.springframework.beans.factory.annotation.Autowired;

public class ResolveService {

    @Autowired
    private ResolveDAO resolveDAO;

    @Autowired
    private RequestService requestService;

    public Resolve createResolve(Resolve resolve){
        return resolveDAO.createResolve(resolve);
    }

    public void deleteResolve(Long resolveId){
        resolveDAO.deleteResolve(resolveId);
    }

    public void approveResolve(Long resolveId){
        resolveDAO.approveResolve(resolveId);
    }

    public Resolve getResolve(Long resolveId){
        return resolveDAO.getResolve(resolveId);
    }
}
