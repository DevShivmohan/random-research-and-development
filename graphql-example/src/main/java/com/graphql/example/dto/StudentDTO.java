package com.graphql.example.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private String name;
    private String branch;
    private int age;
    private boolean isActive;
}
