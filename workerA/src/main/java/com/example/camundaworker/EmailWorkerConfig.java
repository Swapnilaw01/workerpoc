package com.example.camundaworker;

import com.example.workflow.CommonCamundaWorker;
import com.example.workflow.GenericExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@Configuration
public class EmailWorkerConfig {
    @Value("${camunda.engine.url}") //Injects the Camunda engine URL from the application properties or YAML file.
    private String camundaUrl;

    //Dependency Injection of Worker Classes
    private final SendEmailWorker sendEmailWorker;
    private final SendWelcomeEmailWorker sendWelcomeEmailWorker;

    //Constructor for Dependency Injection
    //The constructor injects instances of SendEmailWorker and SendWelcomeEmailWorker using Spring’s dependency injection
    public EmailWorkerConfig(SendEmailWorker sendEmailWorker, SendWelcomeEmailWorker sendWelcomeEmailWorker) {
        this.sendEmailWorker = sendEmailWorker;
        this.sendWelcomeEmailWorker = sendWelcomeEmailWorker;
    }

    @Bean //This method registers CommonCamundaWorker as a Spring bean, making it accessible throughout the application.
    public CommonCamundaWorker emailWorker() {
        Map<String, BiConsumer<ExternalTask, ExternalTaskService>> topicHandlers = new HashMap<>();
        topicHandlers.put("sendEmail", this::handleSendEmailTask);
        topicHandlers.put("sendWelcomeEmail", this::handleSendWelcomeEmailTask);

        // Return the common worker with the generic task handler
        //Creates and returns a CommonCamundaWorker instance, which:
        //Connects to Camunda engine (camundaUrl).
        //Subscribes to two topics: sendEmail and sendWelcomeEmail.
        //Uses GenericExternalTaskHandler(topicHandlers), which dynamically dispatches tasks to the correct handler.
        return new CommonCamundaWorker(camundaUrl, Arrays.asList("sendEmail", "sendWelcomeEmail"),
                new GenericExternalTaskHandler(topicHandlers));
    }


    //This delegates the handling of the sendWelcomeEmail task to SendWelcomeEmailWorker.
    //Why? → The actual logic is inside SendWelcomeEmailWorker, and this method acts as a bridge.
    private void handleSendWelcomeEmailTask(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        sendWelcomeEmailWorker.handleSendWelcomeEmailTask(externalTask, externalTaskService);
    }

    //Similar to handleSendWelcomeEmailTask, this delegates task handling to SendEmailWorker.
    private void handleSendEmailTask(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        sendEmailWorker.handleSendEmailTask(externalTask, externalTaskService);
    }
}
