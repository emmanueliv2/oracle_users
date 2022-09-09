package com.oracle.user.exception.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionModel {
    private String messageCode;
    private String message;
    private String messageDescription;
    private Date createAt;
    private Date modifiedAt;
}
