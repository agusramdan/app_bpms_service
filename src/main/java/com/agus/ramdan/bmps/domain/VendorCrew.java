package com.agus.ramdan.bmps.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "t_vendor_crew")
@Schema
public class VendorCrew {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_sec_vendor_crew_gen")
    @SequenceGenerator(name = "dm_sec_vendor_crew_gen" , sequenceName = "dm_sec_vendor_crew")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    @JsonProperty(index = 2)
    @Schema(description = "Name")
    private String name;

    @Column(name = "vendor_id", nullable = false)
    private Long vendor_id;

    @Column
    private String nik;

    @Column
    private String ktp;

}