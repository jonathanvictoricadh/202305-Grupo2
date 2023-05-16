package com.jmg.checkagro.check.service;

import com.jmg.checkagro.check.exception.CheckException;
import com.jmg.checkagro.check.exception.MessageCode;
import com.jmg.checkagro.check.model.Check;
import com.jmg.checkagro.check.model.CheckState;
import com.jmg.checkagro.check.model.CustomerCheckLimit;
import com.jmg.checkagro.check.model.ProviderCheckLimit;
import com.jmg.checkagro.check.repository.CheckRepository;
import com.jmg.checkagro.check.repository.CustomerCheckLimitRepository;
import com.jmg.checkagro.check.repository.ProviderCheckLimitRepository;
import com.jmg.checkagro.check.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Slf4j
public class CheckService {

    private final CheckRepository checkRepository;
    private final CustomerCheckLimitRepository customerCheckLimitRepository;
    private final ProviderCheckLimitRepository providerCheckLimitRepository;

    public CheckService(CheckRepository checkRepository, CustomerCheckLimitRepository customerCheckLimitRepository, ProviderCheckLimitRepository providerCheckLimitRepository) {
        this.checkRepository = checkRepository;
        this.customerCheckLimitRepository = customerCheckLimitRepository;
        this.providerCheckLimitRepository = providerCheckLimitRepository;
    }

    @Transactional
    public Long create(Check check) throws CheckException {
        check.setStateCheck(CheckState.EMIT.name());
        check.setEmitDate(DateTimeUtils.nowDateTime());
        check.setCommissionAgro(check.getAmountTotal().multiply(BigDecimal.valueOf(0.10)));
        check.getCheckDetails().forEach(checkDetail -> checkDetail.setCheckVirtual(check));

        var total=check.getCheckDetails().stream().map(
                checkDetail -> checkDetail.getAmountUnit().multiply(BigDecimal.valueOf(checkDetail.getQuantity()))
        ).reduce(BigDecimal.ZERO,BigDecimal::add);
        if(total.compareTo(check.getAmountTotal())!=0){
            throw new CheckException(MessageCode.CHECK_NOT_TOTAL_AMOUNT_EQUALS);
        }

        CustomerCheckLimit customer = customerCheckLimitRepository.findById(
                CustomerCheckLimit.CustomerCheckLimitId.builder()
                        .documentTypeCustomer(check.getDocumentTypeCustomer())
                        .documentValueCustomer(check.getDocumentValueCustomer())
                        .build()
        ).orElseThrow(() -> new CheckException(MessageCode.CHECK_CUSTOMER_NOT_FOUND));

        var provider = providerCheckLimitRepository.findById(
                ProviderCheckLimit.ProviderCheckLimitId.builder()
                        .documentTypeProvider(check.getDocumentTypeProvider())
                        .documentValueProvider(check.getDocumentValueProvider())
                        .build()
        ).orElseThrow(() -> new CheckException(MessageCode.CHECK_PROVIDER_NOT_FOUND));

        if (!provider.getActive()) {
            throw new CheckException(MessageCode.CHECK_PROVIDER_NOT_FOUND);
        }

        customer.setCheckAmountConsumed(customer.getCheckAmountConsumed().add(check.getAmountTotal()));
        provider.setCheckAmountActive(provider.getCheckAmountActive().add(check.getAmountTotal()));

        if (customer.getCheckAmountConsumed().compareTo(customer.getCheckAmountLimit()) > 0) {
            throw new CheckException(MessageCode.CUSTOMER_NOT_LIMIT);
        }

        customerCheckLimitRepository.save(customer);
        providerCheckLimitRepository.save(provider);
        checkRepository.save(check);
        return check.getId();
    }

    @Transactional
    public void pay(Long id) throws CheckException {
        var check = checkRepository.findById(id).orElseThrow(() -> new CheckException(MessageCode.CHECK_NOT_FOUND));
        if (!check.getStateCheck().equals(CheckState.EMIT.name())) {
            throw new CheckException(MessageCode.CHECK_NOT_ACTIVE);
        }
        check.setStateCheck(CheckState.PAYED.name());
        CustomerCheckLimit customer = customerCheckLimitRepository.findById(
                CustomerCheckLimit.CustomerCheckLimitId.builder()
                        .documentTypeCustomer(check.getDocumentTypeCustomer())
                        .documentValueCustomer(check.getDocumentValueCustomer())
                        .build()
        ).orElseThrow(()->new CheckException(MessageCode.CHECK_CUSTOMER_NOT_FOUND));

        var provider = providerCheckLimitRepository.findById(
                ProviderCheckLimit.ProviderCheckLimitId.builder()
                        .documentTypeProvider(check.getDocumentTypeProvider())
                        .documentValueProvider(check.getDocumentValueProvider())
                        .build()
        ).orElseThrow(()->new CheckException(MessageCode.CHECK_PROVIDER_NOT_FOUND));

        customer.setCheckAmountPayed(customer.getCheckAmountPayed().add(check.getAmountTotal()));
        customer.setCheckAmountConsumed(customer.getCheckAmountConsumed().subtract(check.getAmountTotal()));
        provider.setCheckAmountReceived(provider.getCheckAmountReceived().add(check.getAmountTotal()));
        provider.setCheckAmountActive(provider.getCheckAmountActive().subtract(check.getAmountTotal()));
        customerCheckLimitRepository.save(customer);
        providerCheckLimitRepository.save(provider);
        checkRepository.save(check);
    }

    @Transactional
    public void cancel(Long id) throws CheckException {
        var check = checkRepository.findById(id).orElseThrow(() -> new CheckException(MessageCode.CHECK_NOT_FOUND));
        if (!check.getStateCheck().equals(CheckState.EMIT.name())) {
            throw new CheckException(MessageCode.CHECK_NOT_ACTIVE);
        }
        check.setStateCheck(CheckState.CANCELED.name());
        var customer = customerCheckLimitRepository.findById(
                CustomerCheckLimit.CustomerCheckLimitId.builder()
                        .documentTypeCustomer(check.getDocumentTypeCustomer())
                        .documentValueCustomer(check.getDocumentValueCustomer())
                        .build()
        ).orElseThrow(()->new CheckException(MessageCode.CHECK_CUSTOMER_NOT_FOUND));

        var provider = providerCheckLimitRepository.findById(
                ProviderCheckLimit.ProviderCheckLimitId.builder()
                        .documentTypeProvider(check.getDocumentTypeProvider())
                        .documentValueProvider(check.getDocumentValueProvider())
                        .build()
        ).orElseThrow(()->new CheckException(MessageCode.CHECK_PROVIDER_NOT_FOUND));

        customer.setCheckAmountConsumed(customer.getCheckAmountConsumed().subtract(check.getAmountTotal()));
        provider.setCheckAmountActive(provider.getCheckAmountActive().subtract(check.getAmountTotal()));
        customerCheckLimitRepository.save(customer);
        providerCheckLimitRepository.save(provider);
        checkRepository.save(check);
    }

    public Check getById(Long id) throws CheckException {
        return checkRepository.findById(id).orElseThrow(() -> new CheckException(MessageCode.CHECK_NOT_FOUND));

    }

    public void registerCustomer(String documentType, String documentValue) {
        log.info("registerCustomer");
        customerCheckLimitRepository.save(
                CustomerCheckLimit.builder()
                        .id(CustomerCheckLimit.CustomerCheckLimitId.builder()
                                .documentTypeCustomer(documentType)
                                .documentValueCustomer(documentValue)
                                .build())
                        .checkAmountConsumed(BigDecimal.ZERO)
                        .checkAmountPayed(BigDecimal.ZERO)
                        .checkAmountLimit(BigDecimal.valueOf(10000000L))
                        .build()
        );
    }

    public void registerProvider(String documentType, String documentValue) {
        log.info("registerProvider");
        providerCheckLimitRepository.save(
                ProviderCheckLimit.builder()
                        .id(ProviderCheckLimit.ProviderCheckLimitId.builder()
                                .documentTypeProvider(documentType)
                                .documentValueProvider(documentValue)
                                .build())
                        .checkAmountActive(BigDecimal.ZERO)
                        .active(true)
                        .checkAmountReceived(BigDecimal.ZERO)
                        .build()
        );
    }

    public void deleteCustomer(String documentType, String documentValue) throws CheckException {
        var customer = customerCheckLimitRepository.findById(
                CustomerCheckLimit.CustomerCheckLimitId.builder()
                        .documentTypeCustomer(documentType)
                        .documentValueCustomer(documentValue)
                        .build()
        ).orElseThrow(() -> new CheckException(MessageCode.CHECK_CUSTOMER_NOT_FOUND));
        customer.setCheckAmountLimit(BigDecimal.ZERO);
        customerCheckLimitRepository.save(customer);
    }

    public void deleteProvider(String documentType, String documentValue) throws CheckException {
        var provider = providerCheckLimitRepository.findById(
                ProviderCheckLimit.ProviderCheckLimitId.builder()
                        .documentTypeProvider(documentType)
                        .documentValueProvider(documentValue)
                        .build()
        ).orElseThrow(() -> new CheckException(MessageCode.CHECK_PROVIDER_NOT_FOUND));
        provider.setActive(false);
        providerCheckLimitRepository.save(provider);

    }
}
