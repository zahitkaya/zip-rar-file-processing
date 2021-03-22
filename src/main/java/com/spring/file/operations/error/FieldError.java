package com.spring.file.operations.error;

import lombok.Value;

import java.io.Serializable;

@Value
public class FieldError implements Serializable {

    String objectName;

    String field;

    String message;
}
