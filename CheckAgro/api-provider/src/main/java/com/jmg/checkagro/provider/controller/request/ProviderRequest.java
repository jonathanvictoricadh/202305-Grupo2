package com.jmg.checkagro.provider.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProviderRequest {

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
