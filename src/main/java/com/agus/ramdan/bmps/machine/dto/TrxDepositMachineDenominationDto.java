package com.agus.ramdan.bmps.machine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrxDepositMachineDenominationDto implements Serializable {
    private Long id;
    private BigDecimal denomination;
    private Integer quantity;
    private BigDecimal amount;
}
