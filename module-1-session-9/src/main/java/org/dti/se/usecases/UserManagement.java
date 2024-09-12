package org.dti.se.usecases;

import org.dti.se.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserManagement {

    @Autowired
    private UserRepository userRepository;

}
