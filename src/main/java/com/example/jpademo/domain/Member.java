package com.example.jpademo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity Member
 */
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {

    private static final Long serialVersionUID = 1L;

    private Integer id;

    @Size(min = 6,message = "Username must longer than 6!")
    @NotEmpty(message = "Username is required")
    private String userName;

    @JsonIgnore
    @NotEmpty(message = "Password is required.")
    private String password;

    @Size(min = 11, max = 11, message = "Mobile no. must be 11 digits.")
    @NotEmpty(message = "Mobile no. is required.")
    private String mobileNumber;

    @NotEmpty(message = "Address no. is required.")
    private String address;

    private String sign;

    @JsonIgnoreProperties("members")
    private List<Worker> workers=new ArrayList<>();


}
