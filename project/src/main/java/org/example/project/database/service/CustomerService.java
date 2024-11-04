package org.example.project.database.service;

import org.example.project.database.model.Customer;
import org.example.project.database.repo.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public void save(Customer customer){
        if (findByID(customer).isEmpty())
            customerRepository.save(customer);
    }

    public Optional<Customer> findByID(Customer customer) {
        return customerRepository.findById(customer.getId());
    }
}
