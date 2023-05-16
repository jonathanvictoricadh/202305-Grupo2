package com.jmg.checkagro.customer.controller.mapper;


import com.jmg.checkagro.customer.controller.request.CustomerRequest;
import com.jmg.checkagro.customer.controller.response.CustomerResponse;
import com.jmg.checkagro.customer.model.Customer;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface  CustomerMapper {


    Customer toCustomer(CustomerRequest request);

    CustomerResponse toCustomerResponse(Customer entity);
}
