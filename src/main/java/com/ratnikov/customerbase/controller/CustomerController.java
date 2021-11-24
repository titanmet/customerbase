package com.ratnikov.customerbase.controller;

import com.ratnikov.customerbase.exeption.ResourceNotFoundException;
import com.ratnikov.customerbase.model.CustomerBase;
import com.ratnikov.customerbase.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public List<CustomerBase> getAllCustomer(){
        return customerRepository.findAll();
    }

    @PostMapping("/customers")
    public CustomerBase createCustomer(@RequestBody CustomerBase customerBase) {
        return customerRepository.save(customerBase);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerBase> getCustomerById(@PathVariable Long id) {
        CustomerBase customerBase = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(customerBase);
    }


    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerBase> updateCustomer(@PathVariable Long id, @RequestBody CustomerBase customerDetails){
        CustomerBase customerBase = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id :" + id));

        customerBase.setName(customerDetails.getName());
        customerBase.setContactPerson(customerDetails.getContactPerson());
        customerBase.setPhone(customerDetails.getPhone());
        customerBase.setEmail(customerDetails.getEmail());
        customerBase.setDescription(customerDetails.getDescription());
        customerBase.setNeed(customerDetails.getNeed());
        customerBase.setManager(customerDetails.getManager());

        CustomerBase updatedCustomer = customerRepository.save(customerBase);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteCustomer(@PathVariable Long id){
        CustomerBase customerBase = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not exist with id :" + id));

        customerRepository.delete(customerBase);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}
