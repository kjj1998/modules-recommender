package com.example.modulesrecommender.controllers;

import com.example.modulesrecommender.models.Module;
import com.example.modulesrecommender.repositories.ModuleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/modules")
public class ModuleController {
    private final ModuleRepository moduleRepository;

    public ModuleController(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @GetMapping("/{courseCode}")
    Optional<Module> byCourseCode(@PathVariable String courseCode) {
        return moduleRepository.findById(courseCode);
    }
}