package com.graphql.example.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
@Builder
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String branch;
    private int age;
    private boolean isActive;
}
