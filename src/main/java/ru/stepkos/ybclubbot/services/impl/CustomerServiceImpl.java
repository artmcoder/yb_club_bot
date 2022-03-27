package ru.stepkos.ybclubbot.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepkos.ybclubbot.cache.CustomerData;
import ru.stepkos.ybclubbot.models.Customer;
import ru.stepkos.ybclubbot.repositories.CustomerRepository;
import ru.stepkos.ybclubbot.services.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public void saveCustomer(CustomerData profileData) {
        Customer customer = Customer.builder()
                .username(profileData.getUsername())
                .name(profileData.getName())
                .answer1(profileData.getAnswer1())
                .answer2(profileData.getAnswer2())
                .answer3(profileData.getAnswer3())
                .build();
        customerRepository.save(customer);
    }

    @Override
    public boolean profileIsFilling(String username) {
        return customerRepository.findByUsername(username).isPresent();
    }
}
