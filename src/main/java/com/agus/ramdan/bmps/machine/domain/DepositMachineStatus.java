package com.agus.ramdan.bmps.machine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "deposit_machine_status")
@Schema
public class DepositMachineStatus {

    @Id
    @JsonProperty(index = 1)
    private Long id;

    @Column(name = "code")
    @JsonProperty(index = 2)
    @Schema(description = "Code")
    private String code;

    @Column(name = "serial_number")
    @JsonProperty(index = 3)
    @Schema(description = "Serial Number")
    private String serial_number;

    @Column(name = "status")
    @JsonProperty(index = 4)
    @Schema(description = "Status, 0 = Uknon, 1 = Offline , 2 = online,  ")
    private int status;

    @Column(name = "percent")
    @JsonProperty(index = 5)
    @Schema(description = "percent capacity (max 100) ")
    private int percent;

    @Column(name = "amount")
    @JsonProperty(index = 6)
    @Schema(description = "Jumlah Uang")
    private BigDecimal amount;

    @JsonProperty(index = 7)
    private LocalDateTime last_pick_money;

    @UpdateTimestamp
    private LocalDateTime updated_on;
}