package com.agus.ramdan.bmps.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class QRCodeRequest implements Serializable {
    private String username;
    private String user_type;
    private String crew_code;
    private String crew_type;
    private String partner_code;
    private String partner_type;
    private String service_product_code;
    private String service_product_name;
    private String partner_trx_no;

    // target bank account
    private String wallet_issuer;
    private String wallet_number;
    private String wallet_name;

    // target bank account
    private String bank_code;
    private String bank_name;
    private String bank_account_number;
    private String bank_account_name;

    // on_behalf
    private String on_behalf_partner_code;
    private String on_behalf_partner_name;

    private LocalDateTime expired_time ;
}
