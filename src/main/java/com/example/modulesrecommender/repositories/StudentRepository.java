package com.example.modulesrecommender.repositories;

import com.example.modulesrecommender.models.Module;
import com.example.modulesrecommender.models.Student;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface StudentRepository extends Repository<Student, String> {
    Optional<Student> findById(String studentId);
}
