package com.agus.ramdan.bmps.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "t_partner")
@Schema
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_sec_partner_gen")
    @SequenceGenerator(name = "dm_sec_partner_gen" , sequenceName = "dm_sec_partner")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @JsonProperty(index = 2)
    @Schema(description = "Name")
    private String name;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_on;
    @UpdateTimestamp
    private LocalDateTime updated_on;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    public String created_by;

    @LastModifiedBy
    @Column(name = "updated_by")
    public String updated_by;

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