package com.example.jpademo.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.hateoas.ResourceSupport;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Entity Member
 */
@Entity
public class Member implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "member_worker",
            joinColumns = @JoinColumn(name="member_id"),
            inverseJoinColumns = @JoinColumn(name = "worker_id"))
    @JsonIgnoreProperties("members")
    private List<Worker> workers=new ArrayList<>();

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Member{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address='" + address + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

}
