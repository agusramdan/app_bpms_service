package com.agus.ramdan.bmps.machine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrxDepositMachineRequest implements Serializable {
    @NotNull(message = "QR Code cannot be null")
    @Size(min = 20, max = 20, message = "QR Code must 20 characters.")
    private String qr_code;
    private String username;
    private String user_type;
    private String partner_code;
    private String partner_type;
    @AssertTrue(message = "Either machine_deposit_code or machine_deposit_sn must be provided.")
    public boolean validMachineDepositInfo() {
        return machine_deposit_code != null || machine_deposit_sn != null;
    }
    private String machine_deposit_code;
    private String machine_deposit_sn;
    private String service_product_code;
    private String service_product_name;
    private String partner_trx_no;
    private String internal_trx_number;
    @NotNull(message = "trx_date cannot be null.")
    private LocalDateTime trx_date;
    @NotNull(message = "amount cannot be null.")
    @Positive(message = "amount must be positive value.")
    private BigDecimal amount;

    @Valid
    @NotNull(message = "denominations cannot be null.")
    @NotEmpty(message = "denominations cannot be empty.")
    private List<TrxDepositMachineDenominationRequest> denominations = new ArrayList<>();
}
