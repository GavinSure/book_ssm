package com.Gavin.service.impl;

import com.Gavin.entity.Admin;
import com.Gavin.entity.AdminExample;
import com.Gavin.mapper.AdminMapper;
import com.Gavin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Admin> findAdminByEmail(String email) {
        AdminExample adminExample=new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andAdminEmailEqualTo(email);
        return adminMapper.selectByExample(adminExample);
    }

    @Override
    public Admin findAdminById(long id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateAdminById(Admin admin) {
        adminMapper.updateByPrimaryKey(admin);
    }
}
