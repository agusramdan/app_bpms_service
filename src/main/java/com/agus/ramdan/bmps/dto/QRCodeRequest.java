package com.agus.ramdan.bmps.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class QRCodeRequest implements Serializable {
    private String username;
    private String user_type;
    private String partner_code;
    private String partner_type;
    private String service_product_code;
    private String service_product_name;
    private String partner_trx_no;
}
