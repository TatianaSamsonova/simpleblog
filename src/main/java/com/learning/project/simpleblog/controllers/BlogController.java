package com.learning.project.simpleblog.controllers;

import com.learning.project.simpleblog.models.Post;
import com.learning.project.simpleblog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("title", "Add Post");
        return "blog-add";
    }

    @PostMapping("/blog/add")
    public String blogPostAdd(@RequestParam String title,
                              @RequestParam String anons,
                              @RequestParam String fullText, Model model){
        Post post = new Post(title, anons, fullText);
        postRepository.save(post);
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long postId, Model model) {
        if( !postRepository.existsById(postId)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(postId);
        List<Post> list = new ArrayList<>();
        post.ifPresent(list::add);
        model.addAttribute("title", "Post Details");
        model.addAttribute("post", list);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long postId, Model model) {
        if (!postRepository.existsById(postId)){
            return "redirect:/blog";
        }

        Optional<Post> post = postRepository.findById(postId);
        List<Post> list = new ArrayList<>();
        post.ifPresent(list::add);
        model.addAttribute("title", "Post Edit");
        model.addAttribute("post", list);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long postId,
                                 @RequestParam String title,
                                 @RequestParam String anons,
                                 @RequestParam String fullText, Model model){
        Post post = postRepository.findById(postId).orElseThrow(RuntimeException::new);
        post.setTitle(title);
        post.setAnons(anons);
        post.setFullText(fullText);
        postRepository.save(post);
        return "redirect:/blog";
    }
}
