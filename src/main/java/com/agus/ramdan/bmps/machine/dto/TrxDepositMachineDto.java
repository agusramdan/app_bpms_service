package com.agus.ramdan.bmps.machine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrxDepositMachineDto implements Serializable {
    private Long id;
    private LocalDateTime created_on;
    private LocalDateTime updated_on;
    private String created_by;
    private String updated_by;
    private String qr_code;
    private String username;
    private String user_type;
    private String partner_code;
    private String partner_type;
    private String machine_deposit_code;
    private String machine_deposit_sn;
    private String service_product_code;
    private String service_product_name;
    private String partner_trx_no;
    private String internal_trx_number;
    private LocalDateTime trx_date;
    private BigDecimal amount;
}
