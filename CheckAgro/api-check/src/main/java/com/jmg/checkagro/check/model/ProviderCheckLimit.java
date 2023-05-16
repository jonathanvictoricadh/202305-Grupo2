package com.jmg.checkagro.check.model;


import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "providerCheckLimit")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProviderCheckLimit {
    @EmbeddedId
    private ProviderCheckLimit.ProviderCheckLimitId id;

    @Column(nullable = false, columnDefinition = "integer default 1")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean active;

    @Column(nullable = false, precision = 17, scale = 2)
    private BigDecimal checkAmountReceived;

    @Column(nullable = false, precision = 17, scale = 2)
    private BigDecimal checkAmountActive;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @Embeddable
    public static class ProviderCheckLimitId implements Serializable {
        @Column(nullable = false, length = 10)
        private String documentTypeProvider;
        @Column(nullable = false, length = 20)
        private String documentValueProvider;
    }
}
