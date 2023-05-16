package com.jmg.checkagro.customer.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false)
    private Long id;
    @Column(nullable = false, length = 10)
    private String documentType;
    @Column(nullable = false, length = 20)
    private String documentNumber;
    @Column(nullable = false, length = 100)
    private String businessName;
    @Column(nullable = false, length = 250)
    private String email;
    @Column(nullable = false, length = 20)
    private String phone;
    @Column(nullable = false)
    private LocalDate creation;
    @Column(nullable = false, columnDefinition = "integer default 1")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean active;

}
