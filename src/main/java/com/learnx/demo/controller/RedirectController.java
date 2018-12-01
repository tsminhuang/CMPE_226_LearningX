package com.learnx.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * For Form redirecting in different sections
 *
 */
@Controller
@RequestMapping()
public class RedirectController {
    @PostMapping("/courses/details/{courseId}")
    public ModelAndView redirectToCoursePage(@PathVariable int courseId){
        String targetUrl="redirect:/courses/"+courseId;
        ModelAndView mav = new ModelAndView(targetUrl);
        return mav;
    }

}