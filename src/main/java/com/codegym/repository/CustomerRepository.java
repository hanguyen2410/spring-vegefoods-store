package com.codegym.repository;

import com.codegym.model.Customer;
import com.codegym.model.dto.CustomerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    @Query("SELECT NEW com.codegym.model.dto.CustomerDTO (" +
            "c.id, " +
            "c.fullName, " +
            "c.locationRegion, " +
            "c.user, " +
            "c.customerAvatar" +
            ") " +
            "FROM Customer AS c " +
            "WHERE c.deleted = false "
    )
    List<CustomerDTO> getAllCustomerDTO();

    @Query("SELECT NEW com.codegym.model.dto.CustomerDTO (" +
            "c.id, " +
            "c.fullName, " +
            "c.locationRegion, " +
            "c.user, " +
            "c.customerAvatar" +
            ") " +
            "FROM Customer AS c " +
            "WHERE c.deleted = false " +
            "AND c.user.username = :email"
    )
    Optional<CustomerDTO> getByEmailDTO(@Param("email") String email);

    @Modifying
    @Query("UPDATE Customer AS s SET s.deleted = true WHERE s.id = :customerId")
    void softDelete(@Param("customerId") Long customerId);




}



