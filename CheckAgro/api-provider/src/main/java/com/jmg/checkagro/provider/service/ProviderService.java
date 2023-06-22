package com.jmg.checkagro.provider.service;

import com.jmg.checkagro.provider.client.CheckMSClient;
import com.jmg.checkagro.provider.event.ProveedorCreadoEventProducer;
import com.jmg.checkagro.provider.exception.MessageCode;
import com.jmg.checkagro.provider.exception.ProviderException;
import com.jmg.checkagro.provider.model.Provider;
import com.jmg.checkagro.provider.repository.ProviderRepository;
import com.jmg.checkagro.provider.utils.DateTimeUtils;
import feign.Feign;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProviderService {

    private final ProviderRepository providerRepository;
    // TODO: 24/05/2023 al agregar acá el feign q está en cliente me pidio q lo meta en el constructor ok?
    private final CheckMSClient checkMSClient;
    @Value("${urlCheck}")
    private String urlCheck;

    private final ProveedorCreadoEventProducer proveedorCreadoEventProducer;



    public ProviderService(ProviderRepository providerRepository, CheckMSClient checkMSClient, ProveedorCreadoEventProducer proveedorCreadoEventProducer) {
        this.providerRepository = providerRepository;
        this.checkMSClient = checkMSClient;
        this.proveedorCreadoEventProducer = proveedorCreadoEventProducer;
    }

    @Transactional
    public Long create(Provider entity) throws ProviderException {
        if(providerRepository.findByDocumentTypeAndDocumentNumber(entity.getDocumentType(),entity.getDocumentNumber()).isPresent()){
            throw new ProviderException(MessageCode.PROVIDER_EXISTS);
        }
        entity.setCreation(DateTimeUtils.now());
        entity.setActive(true);
        providerRepository.save(entity);
//        registerProviderInMSCheck(entity);
        proveedorCreadoEventProducer.publishCrearProveedor(new ProveedorCreadoEventProducer.Data(entity.getId(), entity.getBusinessName()));

        return entity.getId();
    }
    @Retry(name="retryProviderRegister")
    @CircuitBreaker(name="registerProvider", fallbackMethod = "registerProviderInMSCheckFallback")
    private void registerProviderInMSCheck(Provider entity) {
/*
        CheckMSClient client = Feign.builder()
                .encoder(new JacksonEncoder())
                .target(CheckMSClient.class, urlCheck);*/
        checkMSClient.registerProvider(CheckMSClient.DocumentRequest.builder()
                .documentType(entity.getDocumentType())
                .documentValue(entity.getDocumentNumber())
                .build());
        }
    public void registerProviderInMSCheckFallback(Provider entity, Throwable t) throws Exception {
        throw new Exception("No se pudo registrar");
    }

    private void deleteProviderInMSCheck(Provider entity) {

      /*  CheckMSClient client = Feign.builder()
                .encoder(new JacksonEncoder())
                .target(CheckMSClient.class, urlCheck);*/
        checkMSClient.deleteProvider(CheckMSClient.DocumentRequest.builder()
                .documentType(entity.getDocumentType())
                .documentValue(entity.getDocumentNumber())
                .build());
    }

    public void update(Long id, Provider entity) throws ProviderException {
        var entityUpdate = providerRepository.findById(id).orElseThrow(() -> new ProviderException(MessageCode.PROVIDER_NOT_FOUND));
        entity.setDocumentType(entityUpdate.getDocumentType());
        entity.setDocumentNumber(entityUpdate.getDocumentNumber());
        entity.setId(entityUpdate.getId());
        entity.setActive(entityUpdate.getActive());
        entity.setCreation(entityUpdate.getCreation());
        providerRepository.save(entity);
    }

    public void deleteById(Long id) throws ProviderException {
        var entityDelete = providerRepository.findById(id).orElseThrow(() -> new ProviderException(MessageCode.PROVIDER_NOT_FOUND));
        providerRepository.updateActive(false, id);
        deleteProviderInMSCheck(entityDelete);
    }

    public Provider getById(Long id) throws ProviderException {
        return providerRepository.findByIdAndActive(id, true).orElseThrow(() -> new ProviderException(MessageCode.PROVIDER_NOT_FOUND));
    }
}
