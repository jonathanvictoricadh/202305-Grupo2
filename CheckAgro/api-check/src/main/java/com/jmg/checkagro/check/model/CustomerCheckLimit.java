package com.jmg.checkagro.check.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "customerCheckLimit")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerCheckLimit {

    @EmbeddedId
    private CustomerCheckLimit.CustomerCheckLimitId id;

    @Column(nullable = false, precision = 17, scale = 2)
    private BigDecimal checkAmountLimit;

    @Column(nullable = false, precision = 17, scale = 2)
    private BigDecimal checkAmountPayed;

    @Column(nullable = false, precision = 17, scale = 2)
    private BigDecimal checkAmountConsumed;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @Embeddable
    public static class CustomerCheckLimitId implements Serializable {
        @Column(nullable = false, length = 10)
        private String documentTypeCustomer;
        @Column(nullable = false, length = 20)
        private String documentValueCustomer;
    }

}
