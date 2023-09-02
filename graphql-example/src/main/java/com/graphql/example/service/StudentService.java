package com.graphql.example.service;

import com.graphql.example.entity.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(final Student student);
    List<Student> getStudents();
    Student getStudent(int id) throws Throwable;
}
