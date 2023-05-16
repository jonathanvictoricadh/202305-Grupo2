package com.jmg.checkagro.provider.controller;


import com.jmg.checkagro.provider.controller.mapper.ProviderMapper;
import com.jmg.checkagro.provider.controller.request.ProviderRequest;
import com.jmg.checkagro.provider.controller.response.ProviderResponse;
import com.jmg.checkagro.provider.exception.ProviderException;
import com.jmg.checkagro.provider.service.ProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/provider")
public class ProviderController {

    private final ProviderMapper providerMapper;
    private final ProviderService providerService;

    public ProviderController(ProviderMapper providerMapper, ProviderService providerService) {
        this.providerMapper = providerMapper;
        this.providerService = providerService;
    }


    @PostMapping
    public Map<String, Long> create(@RequestBody ProviderRequest request) throws ProviderException {
        return Map.of("id", providerService.create(providerMapper.toProvider(request)));
    }

    @PutMapping("/{id}")
    public void update(@RequestBody ProviderRequest request, @PathVariable Long id) throws ProviderException {
        providerService.update(id, providerMapper.toProvider(request));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) throws ProviderException {
        providerService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ProviderResponse getById(@PathVariable Long id) throws ProviderException {
        return providerMapper.toProviderResponse(providerService.getById(id));
    }


}
