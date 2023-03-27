package com.codegym.api;

import com.codegym.exception.DataInputException;
import com.codegym.model.Customer;
import com.codegym.model.LocationRegion;
import com.codegym.model.dto.CustomerDTO;
import com.codegym.model.dto.CustomerUpdateInformationDTO;
import com.codegym.service.customer.ICustomerService;
import com.codegym.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerAPI {
    @Autowired
    private AppUtils appUtils;

    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllByDeletedIsFalse() {
        List<CustomerDTO> customerDTOS = customerService.getAllCustomerDTO();
        if (customerDTOS.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(customerDTOS, HttpStatus.OK);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<?> getById(@PathVariable String customerId) {
        long sid;
        try {
            sid = Long.parseLong(customerId);
        } catch (NumberFormatException e) {
            throw new DataInputException("ID khách hàng không hợp lệ!!");
        }

        Optional<Customer> customerOptional = customerService.findById(sid);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("ID khách hàng không hợp lệ!!");
        }

        return new ResponseEntity<>(customerOptional.get().toCustomerDTO(), HttpStatus.OK);
    }

    @PatchMapping("{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long customerId, MultipartFile file, @Validated CustomerUpdateInformationDTO customerUpdateInformationDTO, BindingResult bindingResult) {

        Optional<Customer> customerOptional = customerService.findById(customerId);
        if (!customerOptional.isPresent()) {
            throw new DataInputException("ID khách hàng không tồn tại!!");
        }


        Customer customer = customerOptional.get();

        LocationRegion locationRegion = customerUpdateInformationDTO.toLocationRegion();
        locationRegion.setId(customer.getLocationRegion().getId());

        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        customer.setFullName(customerUpdateInformationDTO.getFullName());
        customer.setLocationRegion(locationRegion);
        customer = customerService.saveWithLocationRegion(customer);
        if (file != null) {
            customer = customerService.saveWithAvatar(customer, file);
        }

        return new ResponseEntity<>(customer.toCustomerDTO(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long customerId) {

        Optional<Customer> customerOptional = customerService.findById(customerId);

        if (!customerOptional.isPresent()) {
            throw new DataInputException("ID khách hàng không hợp lệ!!");
        }

        try {
            customerService.softDelete(customerId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new DataInputException("Thao tác không thành công, vui lòng liên hệ Administrator!!");
        }
    }
}