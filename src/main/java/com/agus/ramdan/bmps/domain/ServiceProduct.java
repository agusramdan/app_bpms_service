package com.agus.ramdan.bmps.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "t_service_product")
@Schema
public class ServiceProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_sec_service_product_gen")
    @SequenceGenerator(name = "dm_sec_service_product_gen", sequenceName = "dm_sec_service_product", allocationSize = 1)
    @JsonProperty(index = 1)
    private Long id;

    @Column(name = "name")
    @JsonProperty(index = 2)
    @Schema(description = "Name")
    private String name;

}