package com.learnx.demo.service;

import com.learnx.demo.model.AppUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserServiceImpl implements AppUserService {
    @Override
    public AppUser authenticate(String username, String password) throws Exception {
        return null;
    }

    @Override
    public AppUser Create(String username, String password, AppUser.Role role) {
        return null;
    }

    @Override
    public AppUser getUserById(long userId) {
        return null;
    }

    @Override
    public List<AppUser> listInstructorsByInstituteId(long instituteId) {
        return null;
    }
}
