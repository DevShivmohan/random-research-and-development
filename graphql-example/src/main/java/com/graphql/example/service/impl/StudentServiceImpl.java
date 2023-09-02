package com.graphql.example.service.impl;

import com.graphql.example.entity.Student;
import com.graphql.example.repository.StudentRepository;
import com.graphql.example.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    @Override
    public Student addStudent(Student student) {
        student.setId(0);
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Student getStudent(int id) throws Throwable {
        return studentRepository.findById(id).orElseThrow(()->new Throwable("Student not found"));
    }
}
