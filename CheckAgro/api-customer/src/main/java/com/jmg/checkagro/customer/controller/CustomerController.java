package com.jmg.checkagro.customer.controller;

import com.jmg.checkagro.customer.controller.mapper.CustomerMapper;
import com.jmg.checkagro.customer.controller.request.CustomerRequest;
import com.jmg.checkagro.customer.controller.response.CustomerResponse;
import com.jmg.checkagro.customer.exception.CustomerException;
import com.jmg.checkagro.customer.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    public CustomerController(CustomerMapper customerMapper, CustomerService customerService) {
        this.customerMapper = customerMapper;
        this.customerService = customerService;
    }


    @PostMapping
    public Map<String, Long> create(@RequestBody CustomerRequest request) throws CustomerException {
        return Map.of("id", customerService.create(customerMapper.toCustomer(request)));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody CustomerRequest request, @PathVariable Long id) throws CustomerException {
        customerService.update(id, customerMapper.toCustomer(request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws CustomerException {
        customerService.deleteById(id);
    }

    @GetMapping("/{id}")
    public CustomerResponse getById(@PathVariable Long id) throws CustomerException {
        return customerMapper.toCustomerResponse(customerService.getById(id));
    }


}
