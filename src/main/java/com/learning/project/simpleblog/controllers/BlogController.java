package com.learning.project.simpleblog.controllers;

import com.learning.project.simpleblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/blog")
    public String blog(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        model.addAttribute("title", "Our blog");
        return "blog-main";
    }
}
