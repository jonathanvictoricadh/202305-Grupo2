package com.jmg.checkagro.check.controller;

import com.jmg.checkagro.check.controller.mapper.CheckMapper;
import com.jmg.checkagro.check.controller.response.CheckResponse;
import com.jmg.checkagro.check.controller.response.CustomerCheckLimitResponse;
import com.jmg.checkagro.check.controller.response.ProviderCheckLimitResponse;
import com.jmg.checkagro.check.exception.CheckException;
import com.jmg.checkagro.check.exception.MessageCode;
import com.jmg.checkagro.check.model.CustomerCheckLimit;
import com.jmg.checkagro.check.model.ProviderCheckLimit;
import com.jmg.checkagro.check.repository.CheckRepository;
import com.jmg.checkagro.check.repository.CustomerCheckLimitRepository;
import com.jmg.checkagro.check.repository.ProviderCheckLimitRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/check")
public class CheckQueryController {

    private final CheckRepository checkRepository;
    private final CustomerCheckLimitRepository customerCheckLimitRepository;
    private final ProviderCheckLimitRepository providerCheckLimitRepository;
    private final CheckMapper checkMapper;


    public CheckQueryController(CheckRepository checkRepository, CustomerCheckLimitRepository customerCheckLimitRepository, ProviderCheckLimitRepository providerCheckLimitRepository, CheckMapper checkMapper) {
        this.checkRepository = checkRepository;
        this.customerCheckLimitRepository = customerCheckLimitRepository;
        this.providerCheckLimitRepository = providerCheckLimitRepository;
        this.checkMapper = checkMapper;
    }


    @GetMapping("/find-by-customer/{typeDocument}/{valueDocument}")
    public List<CheckResponse> findByCustomer(@PathVariable String typeDocument, @PathVariable String valueDocument) {
        return checkRepository.findByDocumentTypeCustomerAndDocumentValueCustomer(typeDocument, valueDocument).stream().map(checkMapper::toCheckResponse).toList();
    }

    @GetMapping("/find-by-provider/{typeDocument}/{valueDocument}")
    public List<CheckResponse> findByProvider(@PathVariable String typeDocument, @PathVariable String valueDocument) {
        return checkRepository.findByDocumentTypeProviderAndDocumentValueProvider(typeDocument, valueDocument).stream().map(checkMapper::toCheckResponse).toList();
    }

    @GetMapping("/customer/resume/{typeDocument}/{valueDocument}")
    public CustomerCheckLimitResponse customerResume(@PathVariable String typeDocument, @PathVariable String valueDocument) throws CheckException {
        return checkMapper.toCustomerCheckLimitResponse(customerCheckLimitRepository.findById(
                CustomerCheckLimit.CustomerCheckLimitId.builder()
                        .documentTypeCustomer(typeDocument)
                        .documentValueCustomer(valueDocument)
                        .build()
        ).orElseThrow(
                () -> new CheckException(MessageCode.CHECK_CUSTOMER_NOT_FOUND)
        ));
    }

    @GetMapping("/provider/resume/{typeDocument}/{valueDocument}")
    public ProviderCheckLimitResponse providerResume(@PathVariable String typeDocument, @PathVariable String valueDocument) throws CheckException {
        return checkMapper.toProviderCheckLimitResponse(providerCheckLimitRepository.findById(
                ProviderCheckLimit.ProviderCheckLimitId.builder()
                        .documentTypeProvider(typeDocument)
                        .documentValueProvider(valueDocument)
                        .build()
        ).orElseThrow(
                () -> new CheckException(MessageCode.CHECK_PROVIDER_NOT_FOUND)
        ));
    }

}
