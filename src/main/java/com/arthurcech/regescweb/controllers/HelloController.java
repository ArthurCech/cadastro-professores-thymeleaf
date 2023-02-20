package com.arthurcech.regescweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @GetMapping("/hello")
    public ModelAndView hello() {
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("nome", "Lionel Messi");
        return mv;
    }

    @GetMapping("/hello-model")
    public String hello(Model model) {
        model.addAttribute("nome", "Cristiano Ronaldo");
        return "hello";
    }

    @GetMapping("/hello-servlet")
    public String hello(HttpServletRequest request) {
        request.setAttribute("nome", "Arthur Cech Francisco");
        return "hello";
    }

}
