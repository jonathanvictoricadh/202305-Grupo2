package com.jmg.checkagro.provider.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProviderResponse {

    private Long id;
    private String documentType;
    private String documentNumber;
    private String businessName;
    private String email;
    private String phone;
    private LocalDate creation;

}
