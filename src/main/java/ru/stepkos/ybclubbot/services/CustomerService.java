package ru.stepkos.ybclubbot.services;

import ru.stepkos.ybclubbot.cache.CustomerData;

public interface CustomerService {
    void saveCustomer(CustomerData profileData);

    boolean profileIsFilling(String username);
}
