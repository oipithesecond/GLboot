package com.oipithesecond.glboot.services;

import com.oipithesecond.glboot.domain.entities.User;

import java.util.UUID;

public interface UserService {
    User getUserById(UUID id);
}
