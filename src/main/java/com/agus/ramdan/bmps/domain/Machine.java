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
@Table(name = "t_machine")
@Schema
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_sec_machine_gen")
    @SequenceGenerator(name = "dm_sec_machine_gen", sequenceName = "dm_sec_machine", allocationSize = 1)
    @JsonProperty(index = 1)
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

    @Column(name = "service_location_id")
    @JsonProperty(index = 3)
    @Schema(description = "Service Location ID")
    private Long service_location_id;

    @Column(name = "code")
    @JsonProperty(index = 2)
    @Schema(description = "Code")
    private String code;

    @Column(name = "serial_number")
    @JsonProperty(index = 2)
    @Schema(description = "Serial Number")
    private String serial_number;

}