package com.example.jdbc.controller;

import com.example.jdbc.procedure.CustomStoredProcedureCall;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final CustomStoredProcedureCall customStoredProcedureCall;

    /**
     * Example of stored procedure call with 1 in and 1 out param
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<?> getEmployeeById(@PathVariable int id){
        return ResponseEntity.status(HttpStatus.OK).body(customStoredProcedureCall.getEmployeeName(id));
    }
}
