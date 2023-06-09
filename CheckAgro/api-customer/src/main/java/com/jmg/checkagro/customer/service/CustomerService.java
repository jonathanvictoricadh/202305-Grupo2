package com.jmg.checkagro.customer.service;

import com.jmg.checkagro.customer.client.CheckMSClient;
import com.jmg.checkagro.customer.exception.CustomerException;
import com.jmg.checkagro.customer.exception.MessageCode;
import com.jmg.checkagro.customer.model.Customer;
import com.jmg.checkagro.customer.repository.CustomerRepository;
import com.jmg.checkagro.customer.utils.DateTimeUtils;
import feign.Feign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CheckMSClient checkMSClient;

    @Value("${urlCheck}")
    private String urlCheck;

    public CustomerService(CustomerRepository customerRepository, CheckMSClient checkMSClient) {
        this.customerRepository = customerRepository;
        this.checkMSClient = checkMSClient;
    }

    @Transactional
    public String create(Customer entity) throws CustomerException {
        if(customerRepository.findByDocumentTypeAndDocumentNumber(entity.getDocumentType(),entity.getDocumentNumber()).isPresent()){
            throw new CustomerException(MessageCode.CUSTOMER_EXISTS);
        }
        entity.setCreation(DateTimeUtils.now());
        entity.setActive(true);
        customerRepository.save(entity);

        registerCustomerInMSCheck(entity);

        return entity.getId();
    }

    @Retry(name="retryCustomerRegister")
    @CircuitBreaker(name="registerCustomer", fallbackMethod = "registerCustomerInMSCheckFallback")
    private void registerCustomerInMSCheck(Customer entity) {
        /* TODO: acá reemplacé */
        checkMSClient.registerCustomer(CheckMSClient.DocumentRequest.builder()
                .documentType(entity.getDocumentType())
                .documentValue(entity.getDocumentNumber())
                .build());
    }
    public void registerCustomerInMSCheckFallback(Customer entity, Throwable t) throws Exception {
        throw new Exception("No se pudo registrar");
    }

    private void deleteCustomerInMSCheck(Customer entity) {
       /* TODO: acá reemplacé */
        checkMSClient.deleteCustomer(CheckMSClient.DocumentRequest.builder()
                .documentType(entity.getDocumentType())
                .documentValue(entity.getDocumentNumber())
                .build());
    }

    public void update(String id, Customer entity) throws CustomerException {
        var entityUpdate = customerRepository.findById(id).orElseThrow(() -> new CustomerException(MessageCode.CUSTOMER_NOT_FOUND));
        entity.setDocumentType(entityUpdate.getDocumentType());
        entity.setDocumentNumber(entityUpdate.getDocumentNumber());
        entity.setId(entityUpdate.getId());
        entity.setActive(entityUpdate.getActive());
        entity.setCreation(entityUpdate.getCreation());
        customerRepository.save(entity);
    }

    public void deleteById(String id) throws CustomerException {
        var entityDelete = customerRepository.findById(id).orElseThrow(() -> new CustomerException(MessageCode.CUSTOMER_NOT_FOUND));
        customerRepository.updateActive(false, id);
        deleteCustomerInMSCheck(entityDelete);

    }

    public Customer getById(String id) throws CustomerException {
        return customerRepository.findByIdAndActive(id, true).orElseThrow(() -> new CustomerException(MessageCode.CUSTOMER_NOT_FOUND));
    }
}
