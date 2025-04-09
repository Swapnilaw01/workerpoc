package com.example.camundaworker;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.springframework.stereotype.Component;

@Component
public class SendWelcomeEmailWorker {
    public void handleSendWelcomeEmailTask(ExternalTask task, ExternalTaskService service) {
        String recipient = task.getVariable("email");
        String subject = task.getVariable("subject");
        String message = task.getVariable("message");

        // Logic to send welcome email (simplified)
        System.out.println("Sending welcome email to: " + recipient);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + task.getTopicName());

        // Complete the task after processing
        service.complete(task);
    }
}
