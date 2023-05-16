package com.jmg.checkagro.check.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentRequest {
    @Size(min = 1, max = 10)
    private String documentType;
    @Size(min = 1, max = 20)
    private String documentValue;
}
