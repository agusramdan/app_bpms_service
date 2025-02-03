package com.agus.ramdan.bmps.machine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "trx_deposit_machine_denomination")
@Schema
public class TrxDepositMachineDenomination {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_sec_trx_deposit_machine_denon_gen")
    @SequenceGenerator(name = "dm_sec_trx_deposit_machine_denon_gen",
            sequenceName = "dm_sec_trx_deposit_machine_denon",
            allocationSize = 1,
            initialValue = 10
    )
    @JsonProperty(index = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trx_deposit_machine_id")
    private TrxDepositMachine trxDepositMachine;

    @Column(name = "denomination", precision = 12, scale = 2, nullable = false)
    private BigDecimal denomination;

    private Integer quantity;

    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    @JsonProperty(index = 5)
    protected BigDecimal amount;

    @Pattern(regexp = "coin|note", message = "Invalid type. Only 'coin' or 'note' are allowed.")
    private String type;

}