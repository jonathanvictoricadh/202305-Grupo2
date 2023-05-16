package com.jmg.checkagro.check.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "jmg_checkDetail")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CheckDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "check_Virtual_id", referencedColumnName = "check_Virtual_id"
          )
    private Check checkVirtual;

    @Column(nullable = false, length = 250)
    private String concept;
    @Column(precision = 17, scale = 2,nullable = false)
    private BigDecimal amountUnit;
    @Column(nullable = false)
    private Integer quantity;



}
