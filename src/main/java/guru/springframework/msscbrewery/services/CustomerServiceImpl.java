package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService{

    public CustomerDto getCustomerById(UUID customerId) {
        return CustomerDto.builder()
                .id(UUID.randomUUID())
                .customerName("Simon")
                .build();
    }
}
