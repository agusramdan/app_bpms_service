package com.agus.ramdan.bmps.machine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class TrxDepositMachineResponse implements Serializable {
    private final Long id;
    private final String qr_code;
    private final String username;
    private final String user_type;
    private final String partner_code;
    private final String partner_type;
    private final String machine_deposit_code;
    private final String machine_deposit_sn;
    private final String service_product_code;
    private final String service_product_name;
    private final String partner_trx_no;
    private final String internal_trx_number;
    private final LocalDateTime trx_date;
    private final BigDecimal amount;
    private final List<TrxDepositMachineDenominationDto> denominations;
}
