package com.graphql.example.controller;

import com.graphql.example.dto.StudentDTO;
import com.graphql.example.entity.Student;
import com.graphql.example.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class GraphQLStudentController {
    private final StudentService studentService;

    @MutationMapping("addStudent")
    public Student addStudent(@Argument StudentDTO studentDTO){
        return studentService.addStudent(
                Student.builder()
                        .name(studentDTO.getName())
                        .branch(studentDTO.getBranch())
                        .age(studentDTO.getAge())
                        .isActive(studentDTO.isActive())
                        .build());
    }

    @QueryMapping("getStudent")
    public Student getStudent(@Argument int id) throws Throwable {
        return studentService.getStudent(id);
    }

    @QueryMapping("getStudents")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }
}
