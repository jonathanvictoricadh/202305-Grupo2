package com.jmg.checkagro.customer.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerRequest {

    @Size(min = 1, max = 10)
    private String documentType;
    @Size(min = 1, max = 20)
    private String documentNumber;
    @Size(min = 1, max = 100)
    private String businessName;
    @Size(min = 1, max = 250)
    private String email;
    @Size(min = 1, max = 20)
    private String phone;
}
