package com.oracle.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;
    @Column()
    private String street;
    @Column()
    private String county;
    @Column()
    private String city;
    @Column()
    private String state;
    @Column(name = "postal_code")
    private String postalCode;
    @Column()
    private String country;
    @JsonIgnore
    @OneToOne(mappedBy = "address")
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_id")
    private PhoneDetails phoneDetails;
}

