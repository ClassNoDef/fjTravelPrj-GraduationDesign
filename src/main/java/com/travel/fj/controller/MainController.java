package com.travel.fj.controller;

import com.travel.fj.domain.Attraction;
import com.travel.fj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*checked*/
@Controller
public class MainController {
    @Autowired
    CityService cityService;
    @Autowired
    AttractionService attractionService;
    @Autowired
    AttractionTypeService attractionTypeService;
    @Autowired
    WorkService workService;
    @Autowired
    GuideService guideService;
    @RequestMapping("/")
    public String toMainPage(Model model){

        //获取最流行景点(暂时未用)
        Attraction topPopularAttrac=attractionService.getPopularAttrac().get(0);

        //分城市列表
        model.addAttribute("city",cityService.loadAllCity());

        //随机游记
        model.addAttribute("randomWork",workService.getRandomWorkU());

        //随机指南
        model.addAttribute("randomGuide",guideService.getRandomGuideU());

        //最流行景点(暂时未用)
        model.addAttribute("topPopularAttrac",topPopularAttrac);

        //景点排行
        model.addAttribute("popularAttrac",attractionService.getPopularAttrac());

        //随机景点
        model.addAttribute("randomAttrac",attractionService.getRandomAttrac());

        //返回主页内容
        return "main_page/index";
    }

}
