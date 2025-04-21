package com.example.camundaworker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.camundaworker.service.ProcessService;

@RestController
@RequestMapping("/api/process")
public class ProcessStartController {
    @Autowired
    ProcessService processService;

    // Start a process instance by processDefinitionKey
    //http://localhost:8081/api/process/start-myWorker?myWorkerId=12345
    @PostMapping("/start-myWorker")
    public ResponseEntity<String> startProcess(@RequestParam String myWorkerId){
        System.out.println("myWorkerId ZZZZZ "+myWorkerId);
        String result = processService.startMyWorkerProcess(myWorkerId);
        return ResponseEntity.ok(result);
    }

}
