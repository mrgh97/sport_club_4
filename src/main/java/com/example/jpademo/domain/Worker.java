package com.example.jpademo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.List;

@Entity
public class Worker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, message = "First Name must be at least 2 characters.")
    @NotEmpty(message = "First Name is required.")
    private String firstName;

    @Size(min = 2, message = "Last Name must be at least 2 characters.")
    @NotEmpty(message = "Last Name is required.")
    private String lastName;

    @Size(min = 11, max = 11, message = "Mobile no. must be 11 digits.")
    @NotEmpty(message = "Mobile no. is required.")
    private String mobileNumber;

    @NotEmpty(message = "Address is required.")
    private String address;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "member_worker",
            joinColumns = @JoinColumn(name = "worker_id"),
            inverseJoinColumns = @JoinColumn(name = "member_id"))
    @JsonIgnoreProperties("workers")
    private List<Member> members = new ArrayList<>();

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Worker worker = (Worker) o;
        return id == worker.id &&
                Objects.equals(firstName, worker.firstName) &&
                Objects.equals(lastName, worker.lastName) &&
                Objects.equals(mobileNumber, worker.mobileNumber) &&
                Objects.equals(address, worker.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, mobileNumber, address);
    }
}
