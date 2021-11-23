package com.ratnikov.customerbase.repository;

import com.ratnikov.customerbase.model.CustomerBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerBase, Long> {
}
