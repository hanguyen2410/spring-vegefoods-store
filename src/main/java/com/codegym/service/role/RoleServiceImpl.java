package com.codegym.service.role;
import com.codegym.model.Role;
import com.codegym.model.dto.RoleDTO;
import com.codegym.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long id) {
        return null;
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public List<RoleDTO> getAllRoleDTO() {
        return roleRepository.getAllRoleDTO();
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void removeById(Long id) {

    }

    @Override
    public void remove(Role role) {

    }




    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
