package com.leadgen.backend.model;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String type;

}
