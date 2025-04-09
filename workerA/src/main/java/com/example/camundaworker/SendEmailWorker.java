package com.example.camundaworker;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SendEmailWorker {
    private static final Logger logger = LoggerFactory.getLogger(SendEmailWorker.class);
    public void handleSendEmailTask(ExternalTask task, ExternalTaskService service) {
        logger.info("Processing email task for recipient: {}", Optional.ofNullable(task.getVariable("email")));
        try {
            String recipient = task.getVariable("email");
            String subject = task.getVariable("subject");
            String message = task.getVariable("message");

            // Logic to send email (simplified)
            System.out.println("Sending email to: " + recipient);
            System.out.println("Subject: " + subject);
            System.out.println("Message: " + task.getTopicName());

            // Complete the task after processing
            service.complete(task);
        } catch (Exception e){
            logger.error("Failed to process email task: {}", e.getMessage(), e);
            service.handleFailure(task, "Email Processing Failed", e.getMessage(), 0, 1000);
        }
    }
}
