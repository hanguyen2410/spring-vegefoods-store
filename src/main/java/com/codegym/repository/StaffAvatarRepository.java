package com.codegym.repository;

import com.codegym.model.StaffAvatar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffAvatarRepository extends JpaRepository<StaffAvatar, String> {
}
