package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer save (Customer customer) {
        return this.customerRepository.save(customer);
    }

    public Customer get (Long customerId) {
        return this.customerRepository.findById(customerId).get();
    }

    public List<Customer> getAllCustomers () {
        return this.customerRepository.findAll();
    }
}
