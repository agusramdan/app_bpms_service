package com.agus.ramdan.bmps.machine.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "trx_deposit_machine")
@Schema
@EntityListeners(AuditingEntityListener.class)
public class TrxDepositMachine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dm_sec_trx_deposit_machine_gen")
    @SequenceGenerator(name = "dm_sec_trx_deposit_machine_gen",
            sequenceName = "dm_sec_trx_deposit_machine", allocationSize = 1,
            initialValue = 10
    )
    @JsonProperty(index = 1)
    private Long id;

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

    @Column(unique = true)
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
    @Column(name = "amount", precision = 12, scale = 2, nullable = false)
    @Schema(example = "10000.00", required = true)
    protected BigDecimal amount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "trx_deposit_machine_id")
    @OrderBy("denomination")
    private List<TrxDepositMachineDenomination> denominations = new ArrayList<>();

}