package com.konlamp.rallyepulse.controller;

import com.konlamp.rallyepulse.service.CarClassService;
import com.konlamp.rallyepulse.service.CategoryService;
import com.konlamp.rallyepulse.service.EmailService;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final EmailService emailService;


    @Autowired
    public CategoryController(CategoryService categoryService, EmailService emailService) {
        this.categoryService = categoryService;
        this.emailService = emailService;
    }



}
