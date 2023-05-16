package com.jmg.checkagro.check.controller;

import com.jmg.checkagro.check.controller.mapper.CheckMapper;
import com.jmg.checkagro.check.controller.request.CheckRequest;
import com.jmg.checkagro.check.controller.response.CheckResponse;
import com.jmg.checkagro.check.exception.CheckException;
import com.jmg.checkagro.check.service.CheckPrintService;
import com.jmg.checkagro.check.service.CheckService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/check")
public class CheckController {


    private final CheckMapper checkMapper;
    private final CheckService checkService;
    private final CheckPrintService checkPrintService;

    public CheckController(CheckMapper checkMapper, CheckService checkService, CheckPrintService checkPrintService) {
        this.checkMapper = checkMapper;
        this.checkService = checkService;
        this.checkPrintService = checkPrintService;
    }


    @PostMapping
    public Map<String, Long> create(@RequestBody CheckRequest request) throws CheckException {
        return Map.of("id", checkService.create(checkMapper.toCheck(request)));
    }

    @PutMapping("/pay/{id}")
    public void pay(@PathVariable Long id) throws CheckException {
        checkService.pay(id);
    }

    @PutMapping("/cancel/{id}")
    public void cancel(@PathVariable Long id) throws CheckException {
        checkService.cancel(id);
    }


    @GetMapping("/{id}")
    public CheckResponse getById(@PathVariable Long id) throws CheckException {
        return checkMapper.toCheckResponse(checkService.getById(id));
    }

    @GetMapping("/print/{id}")
    public void print(@PathVariable Long id,HttpServletResponse response) throws CheckException, IOException {
        byte[] fileBytes = checkPrintService.printCheck(id);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=checkVirtual.pdf");
        response.setContentLength(fileBytes.length);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(fileBytes);
        outputStream.flush();
        outputStream.close();
    }
}
