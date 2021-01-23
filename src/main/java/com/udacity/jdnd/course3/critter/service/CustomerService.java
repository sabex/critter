/* (C)2021 */
package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import java.util.List;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerService {

  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Transactional
  public Customer save(Customer customer) {
    return this.customerRepository.save(customer);
  }

  @Transactional
  public Customer addPet(Customer customer, Pet pet) {
    customer.getPets().add(pet);
    return this.customerRepository.save(customer);
  }

  public Customer get(Long customerId) {
    return this.customerRepository.findById(customerId).get();
  }

  public List<Customer> getAllCustomers() {
    return this.customerRepository.findAll();
  }
  }
