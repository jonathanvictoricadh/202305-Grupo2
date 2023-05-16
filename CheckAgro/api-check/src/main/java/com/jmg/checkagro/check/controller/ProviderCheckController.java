package com.jmg.checkagro.check.controller;

import com.jmg.checkagro.check.controller.request.DocumentRequest;
import com.jmg.checkagro.check.exception.CheckException;
import com.jmg.checkagro.check.service.CheckService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/check/provider")
public class ProviderCheckController {


    private final CheckService checkService;

    public ProviderCheckController(CheckService checkService) {
        this.checkService = checkService;
    }


    @PostMapping("/register")
    public void register(@RequestBody DocumentRequest request) throws CheckException {
        checkService.registerProvider(request.getDocumentType(), request.getDocumentValue());
    }

    @PostMapping("/delete")
    public void delete(@RequestBody DocumentRequest request) throws CheckException {
        checkService.deleteProvider(request.getDocumentType(), request.getDocumentValue());
    }


}
