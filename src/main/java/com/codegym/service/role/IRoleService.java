package com.codegym.service.role;

import com.codegym.model.Role;
import com.codegym.model.dto.RoleDTO;
import com.codegym.service.IGeneralService;

import java.util.List;

public interface IRoleService extends IGeneralService<Role> {
    Role findByName(String name);
    List<RoleDTO> getAllRoleDTO();
}