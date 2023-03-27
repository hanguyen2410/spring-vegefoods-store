package com.codegym.service.staffAvatar;

import com.codegym.model.StaffAvatar;
import com.codegym.repository.StaffAvatarRepository;
import com.codegym.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class StaffAvatarServiceImpl implements IStaffAvatarService{
    @Autowired
    StaffAvatarRepository staffAvatarRepository;

    @Override
    public List<StaffAvatar> findAll() {
        return null;
    }

    @Override
    public StaffAvatar getById(Long id) {
        return null;
    }

    @Override
    public Optional<StaffAvatar> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public StaffAvatar save(StaffAvatar staffAvatar) {
        return staffAvatarRepository.save(staffAvatar);
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public void remove(StaffAvatar staffAvatar) {

    }

    @Override
    public void delete(String id){
        staffAvatarRepository.deleteById(id);
    }
}
