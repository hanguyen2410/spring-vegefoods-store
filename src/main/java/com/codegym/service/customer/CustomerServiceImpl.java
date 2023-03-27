package com.codegym.service.customer;

import com.codegym.exception.DataInputException;
import com.codegym.model.*;
import com.codegym.model.dto.CustomerCreateDTO;
import com.codegym.model.dto.CustomerDTO;
import com.codegym.model.enums.FileType;
import com.codegym.repository.*;
import com.codegym.service.customerAvatar.ICustomerAvatarService;
import com.codegym.service.locationRegion.ILocationRegionService;
import com.codegym.service.upload.IUploadService;
import com.codegym.service.user.IUserService;
import com.codegym.utils.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ILocationRegionService locationRegionService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ICustomerAvatarService customerAvatarService;

    @Autowired
    private IUploadService uploadService;

    @Autowired
    private UploadUtil uploadUtil;

    @Override
    public List<Customer> findAll() {
        return null;
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        userService.save(customer.getUser());
        locationRegionService.save(customer.getLocationRegion());
        return customerRepository.save(customer);
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public void remove(Customer customer) {
        customerRepository.delete(customer);
    }

    @Override
    public Customer saveWithLocationRegion(Customer customer) {
        locationRegionService.save(customer.getLocationRegion());
        return customerRepository.save(customer);
    }

    @Override
    public List<CustomerDTO> getAllCustomerDTO() {
        return customerRepository.getAllCustomerDTO();
    }

    @Override
    public Optional<CustomerDTO> getByEmailDTO(String email) {
        return customerRepository.getByEmailDTO(email);
    }



    @Override
    public void softDelete(Long customerId) {
        customerRepository.softDelete(customerId);
    }



    @Override
    public Customer createCustomerWithAvatar(CustomerCreateDTO customerCreateDTO, LocationRegion locationRegion, User user) {
        Customer customer = new Customer();
        String fullName = customerCreateDTO.getFullName();
        locationRegion = locationRegionService.save(locationRegion);
        user = userService.save(user);

        MultipartFile file = customerCreateDTO.getFile();
        String fileType = file.getContentType();
        assert fileType != null;
        fileType = fileType.substring(0, 5);

        CustomerAvatar customerAvatar = new CustomerAvatar();
        customerAvatar.setFileType(fileType);
        customerAvatar = customerAvatarService.save(customerAvatar);

        if (fileType.equals(FileType.IMAGE.getValue())) {
            customerAvatar = uploadAndSaveCustomerAvatar(file, customerAvatar);
        }
        customer.setId(null);
        customer.setFullName(fullName);
        customer.setLocationRegion(locationRegion);
        customer.setUser(user);
        customer.setCustomerAvatar(customerAvatar);
        customer = customerRepository.save(customer);

        return customer;
    }

    @Override
    public Customer saveWithAvatar(Customer customer, MultipartFile file) {
        CustomerAvatar oldCustomerAvatar = customer.getCustomerAvatar();
        try{
            uploadService.destroyImage(oldCustomerAvatar.getCloudId(),uploadUtil.buildImageDestroyParams(customer,oldCustomerAvatar.getCloudId()));
            customerAvatarService.delete(oldCustomerAvatar.getId());
            String fileType = file.getContentType();
            assert fileType != null;
            fileType = fileType.substring(0, 5);
            CustomerAvatar customerAvatar = new CustomerAvatar();
            customerAvatar.setFileType(fileType);
            customerAvatar = customerAvatarService.save(customerAvatar);

            if (fileType.equals(FileType.IMAGE.getValue())) {
                customerAvatar = uploadAndSaveCustomerAvatar(file, customerAvatar);
            }
            customer.setCustomerAvatar(customerAvatar);
            customer = customerRepository.save(customer);
            return customer;
        } catch (IOException e) {
            throw new DataInputException("Xóa hình ảnh thất bại.");
        }

    }

    private CustomerAvatar uploadAndSaveCustomerAvatar(MultipartFile file, CustomerAvatar customerAvatar) {
        try {
            Map uploadResult = uploadService.uploadImage(file, uploadUtil.buildImageUploadParams(customerAvatar));
            String fileUrl = (String) uploadResult.get("secure_url");
            String fileFormat = (String) uploadResult.get("format");

            customerAvatar.setFileName(customerAvatar.getId() + "." + fileFormat);
            customerAvatar.setFileUrl(fileUrl);
            customerAvatar.setFileFolder(UploadUtil.CUSTOMER_IMAGE_UPLOAD_FOLDER);
            customerAvatar.setCloudId(customerAvatar.getFileFolder() + "/" + customerAvatar.getId());
            return customerAvatarService.save(customerAvatar);
        } catch (IOException e) {
            e.printStackTrace();
            throw new DataInputException("Upload hình ảnh thất bại.");
        }
    }
}
