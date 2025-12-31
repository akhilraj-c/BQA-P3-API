package com.mindteck;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * Event listener to ensure that Application is ready
 *
 * @Author Jobin Jacob Paily
 */
@Configuration
@Slf4j
public class StartupEventListener {
    @EventListener(ApplicationReadyEvent.class)
    public void minteckInit() {
        LOGGER.info("");
        LOGGER.info("<================================>");
        LOGGER.info("MindTeck Backend app is up now ! ! !");
        LOGGER.info("<================================>");

    }
}
