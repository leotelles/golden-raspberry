package com.github.leotelles.goldenraspberry;

import com.github.leotelles.goldenraspberry.service.FileService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StartupApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final FileService fileService;

    public StartupApplicationListener(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        fileService.loadAndPersistCsvFile("src/main/resources/movielist.csv");
    }

}
