package com.travel.fj.runner;

import com.travel.fj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;


@Component
public class initRunner implements ApplicationRunner {

    @Autowired
    ServletContext servletContext;
    @Autowired
    NoteTypeService noteTypeService;
    @Autowired
    GuideTypeService guideTypeService;
    @Override
    public void run(ApplicationArguments args)  {

        servletContext.setAttribute("wType",noteTypeService.loadAllNoteType());
        servletContext.setAttribute("gType",guideTypeService.loadAllGuideType()) ;

    }
}
