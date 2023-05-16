package com.jmg.checkagro.provider.controller.mapper;


import com.jmg.checkagro.provider.controller.request.ProviderRequest;
import com.jmg.checkagro.provider.controller.response.ProviderResponse;
import com.jmg.checkagro.provider.model.Provider;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ProviderMapper {


    Provider toProvider(ProviderRequest request);

    ProviderResponse toProviderResponse(Provider entity);
}
