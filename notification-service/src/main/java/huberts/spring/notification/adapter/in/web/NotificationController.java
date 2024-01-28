package huberts.spring.notification.adapter.in.web;

import huberts.spring.notification.domain.model.NotificationDomainModel;
import huberts.spring.notification.domain.port.in.NotificationServicePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final Logger LOGGER = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationServicePort service;

    @GetMapping
    public List<NotificationDomainModel> getAllNotifications() {
        LOGGER.info(">> NotificationController: getting all notifications");
        return service.getAllNotifications();
    }

    @GetMapping("/{notificationId}")
    public NotificationDomainModel getNotificationById(@PathVariable Long notificationId) {
        LOGGER.info(">> NotificationController: getting all notifications");
        return service.getNotificationById(notificationId);
    }
}