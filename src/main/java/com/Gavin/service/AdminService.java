package com.Gavin.service;

import com.Gavin.entity.Admin;

import java.util.List;

public interface AdminService {
    List<Admin> findAdminByEmail(String email);

    Admin findAdminById(long id);

    void updateAdminById(Admin admin);
}
