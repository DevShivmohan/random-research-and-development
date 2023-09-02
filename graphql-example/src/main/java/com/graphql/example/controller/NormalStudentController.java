package com.graphql.example.controller;

import com.graphql.example.entity.Student;
import com.graphql.example.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class NormalStudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.addStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable int id) throws Throwable {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudent(id));
    }

    @GetMapping
    public ResponseEntity<?> getStudents() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudents());
    }

}
