package com.agus.ramdan.bmps.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_customer")
@Schema
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_sec_customer_gen")
    @SequenceGenerator(name = "dm_sec_customer_gen", sequenceName = "dm_sec_customer", allocationSize = 1)
    @JsonProperty(index = 1)
    private Long id;

    @Column(name = "name")
    @JsonProperty(index = 2)
    @Schema(description = "Name")
    private String name;

    // Address Start
    @Column
    private String street1;
    @Column
    private String street2;
    @Column
    private String city;
    @Column
    private String zip_code;
    @Column
    private String country;
    // Address End

}