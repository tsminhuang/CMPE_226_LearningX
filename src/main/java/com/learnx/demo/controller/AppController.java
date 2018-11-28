package com.learnx.demo.controller;

import com.learnx.demo.entity.AppUser;
import com.learnx.demo.model.*;
import com.learnx.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class AppController {

    @Autowired
    AppUserService appuserService;

    @Autowired
    SeriesService seriesService;

    @Autowired
    CourseService courseService;

    @Autowired
    RatingService rateService;

    @Autowired
    DiscussionService discussionService;

    @Autowired
    MaterialService materialService;

    @Autowired
    HomeworkService homeworkService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("seriesList", seriesService.listSeries());
        return mav;
    }

    //  ======================
    //        login
    //  ======================

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("login");
        return mav;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request, HttpSession session) {
        ModelAndView mav = null;
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try{
            AppUserDto user = appuserService.authenticate(username, password);
            mav = new ModelAndView("index");
            mav.addObject("seriesList", seriesService.listSeries());
            mav.addObject("user", user);
            // === add session ===
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userid", user.getId());
        } catch(Exception e){
            mav.addObject("message", e.getMessage());
            mav = new ModelAndView("login");
        }
        return mav;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView showLogout(HttpServletRequest request , HttpSession session) {
        ModelAndView mav = new ModelAndView("index");
        session.invalidate();
        mav.addObject("seriesList", seriesService.listSeries());
        return mav;
    }


    //  ======================
    //         sign up
    //  ======================

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView showRegister() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("user", new AppUser());
        return mav;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, @ModelAttribute("user") AppUserDto newUser) {
        ModelAndView mav = null;
        try {
            AppUserDto user = appuserService.create(newUser.getUsername(), newUser.getPassword(), newUser.getRole());
            mav = new ModelAndView("login");
        } catch (Exception e) {
            mav.addObject("message", e.getMessage());
            mav = new ModelAndView("register");
        }
        return mav;
    }


    //  ======================
    //        search
    //  ======================

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("search_result");
        List<Course> courseList = courseService.searchCourses(request.getParameter("keyword"));
        mav.addObject("courseList", courseList);
        return mav;
    }

    //  ======================
    //      single series
    //  ======================

    @RequestMapping(value = "/singleSeries", method = RequestMethod.POST)
    public ModelAndView singleSeries(HttpServletRequest request) {
        int seriesId = Integer.valueOf(request.getParameter("id"));
        ModelAndView mav = new ModelAndView("search_result");
        List<Course> courseList = seriesService.listCoursesBySeriesId(seriesId);
        mav.addObject("courseList", courseList);
        return mav;
    }


    //  ======================
    //      single courses
    //  ======================

    @RequestMapping(value = "/singleCourse", method = RequestMethod.POST)
    public ModelAndView singleCourses(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("single_course");
        int courseId = Integer.valueOf(request.getParameter("id"));
        HttpSession session = request.getSession(false);
        if(session != null) {
            int userid = Integer.valueOf(String.valueOf(session.getAttribute("userid")));
            mav.addObject("enroll", courseService.isEnrolled(courseId, userid));
            mav.addObject("complete", courseService.isComplete(courseId, userid));
        }

        Course course = courseService.getCourseById(courseId);
        List<Discussion> discussionList = course.getDiscussions();
        mav.addObject("course", course);
        mav.addObject("discussionList", discussionList);
        mav.addObject("discussion", new Discussion());
        mav.addObject("average_rating", rateService.getAverageRatingByCourseId(courseId));
        return mav;
    }

    //  ======================
    //      all courses
    //  ======================

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public ModelAndView allCourses(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("courses");
        List<Course> courseList = courseService.listCourses();
        List<Course> mostPopList = courseService.listCoursesSortedByRating(false);
        List<Course> leastPopList = courseService.listCoursesSortedByRating(true);
        mav.addObject("courseList", courseList);
        mav.addObject("mostPopList", mostPopList);
        mav.addObject("leastPopList", leastPopList);
        return mav;
    }

    //  ======================
    //        Ratings
    //  ======================

    @RequestMapping(value = "/getRatingByCourseId", method = RequestMethod.POST)
    public ModelAndView getreviews(HttpServletRequest request) {
        int courseId = Integer.valueOf(request.getParameter("courseid"));
        ModelAndView mav = new ModelAndView("course_review");
        Course course = courseService.getCourseById(courseId);
        List<Rating> rateList =  rateService.listRatingsByCourseId(courseId);
        mav.addObject("rateList", rateList);
        mav.addObject("course", course);
        return mav;
    }

    //  ======================
    //        homework
    //  ======================

    @RequestMapping(value = "/getHomeworkByCourseId", method = RequestMethod.POST)
    public ModelAndView gethomework(HttpServletRequest request) {
        int courseId = Integer.valueOf(request.getParameter("courseid"));
        ModelAndView mav = new ModelAndView("course_homework");
        Course course = courseService.getCourseById(courseId);
        List<Homework> homeworkList = homeworkService.listHomeworkdsByCourseId(courseId);
        mav.addObject("homeworkList", homeworkList);
        mav.addObject("course", course);
        return mav;
    }

    //  ======================
    //        Material
    //  ======================

    @RequestMapping(value = "/getMaterialByCourseId", method = RequestMethod.POST)
    public ModelAndView getMaterial(HttpServletRequest request) {
        int courseId = Integer.valueOf(request.getParameter("courseid"));
        ModelAndView mav = new ModelAndView("course_material");
        Course course = courseService.getCourseById(courseId);
        List<Material> materialList = materialService.listMaterialsByCourseId(courseId);
        mav.addObject("materialList", materialList);
        mav.addObject("course", course);
        return mav;
    }

    //  ======================
    //       discusssion
    //  ======================

    @RequestMapping(value = "/discussion", method = RequestMethod.POST)
    public ModelAndView postDiscussion(HttpServletRequest request, @ModelAttribute("discussion") Discussion newDiscussion) {
        int userid = Integer.valueOf(request.getParameter("userid"));
        int courseid = Integer.valueOf(request.getParameter("courseid"));
        ModelAndView mav = new ModelAndView("course_homework");
        Course course = courseService.getCourseById(courseid);
        AppUserDto user = appuserService.getUserById(userid);
        newDiscussion.setAppUserDto(user);
        newDiscussion.setCourse(course);

        discussionService.create(newDiscussion);
        List<Discussion> discussionList = discussionService.listDiscussionsByCourseId(courseid);
        mav.addObject("course", course);
        mav.addObject("discussionList", discussionList);
        return mav;
    }

    //  ======================
    //      enroll courses
    //  ======================

    @RequestMapping(value = "/enrollCourse", method = RequestMethod.POST)
    public ModelAndView enrollCourse(HttpServletRequest request) {
        int userId = Integer.valueOf(request.getParameter("userid"));
        int courseId = Integer.valueOf(request.getParameter("courseid"));
        ModelAndView mav = new ModelAndView("single_course");
        courseService.enroll(appuserService.getUserById(userId), courseService.getCourseById(courseId));
        Course course = courseService.getCourseById(courseId);
        List<Discussion> discussionList = course.getDiscussions();
        mav.addObject("enroll", courseService.isEnrolled(courseId, userId));
        mav.addObject("complete", courseService.isComplete(courseId, userId));
        mav.addObject("course", course);
        mav.addObject("discussionList", discussionList);
        mav.addObject("discussion", new Discussion());
        mav.addObject("average_rating", rateService.getAverageRatingByCourseId(courseId));
        return mav;
    }
}
