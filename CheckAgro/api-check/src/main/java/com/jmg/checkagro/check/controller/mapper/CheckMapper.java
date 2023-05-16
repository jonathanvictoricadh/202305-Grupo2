package com.jmg.checkagro.check.controller.mapper;


import com.jmg.checkagro.check.controller.request.CheckRequest;
import com.jmg.checkagro.check.controller.response.CheckResponse;
import com.jmg.checkagro.check.controller.response.CustomerCheckLimitResponse;
import com.jmg.checkagro.check.controller.response.ProviderCheckLimitResponse;
import com.jmg.checkagro.check.model.Check;
import com.jmg.checkagro.check.model.CustomerCheckLimit;
import com.jmg.checkagro.check.model.ProviderCheckLimit;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface CheckMapper {


    Check toCheck(CheckRequest request);

    CheckResponse toCheckResponse(Check entity);

    CustomerCheckLimitResponse toCustomerCheckLimitResponse(CustomerCheckLimit entity);

    ProviderCheckLimitResponse toProviderCheckLimitResponse(ProviderCheckLimit entity);
}
