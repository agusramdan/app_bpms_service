package com.agus.ramdan.bmps.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "qr_code", uniqueConstraints = {
        @UniqueConstraint(name = "uc_qrcode_code", columnNames = {"code"})
})
@Schema
@EntityListeners(AuditingEntityListener.class)
public class QRCode {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_sec_qr_code_gen")
    @SequenceGenerator(name = "dm_sec_qr_code_gen", sequenceName = "dm_sec_qr_code", allocationSize = 1)
    @JsonProperty(index = 1)
    private Long id;

    @Column(name = "code")
    @JsonProperty(index = 2)
    @Schema(description = "QR Code")
    private String code;

    // User Request
    private String username;
    private String user_type;

    // Partner Request
    private Long partner_id;
    private String partner_code;
    private String partner_type;

    // Service Product
    private Long service_product_id;
    private String service_product_code;
    private String service_product_name;

    private String partner_trx_no;

    private Boolean is_used;

}