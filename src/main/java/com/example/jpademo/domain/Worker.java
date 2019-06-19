package com.example.jpademo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String address;

}
