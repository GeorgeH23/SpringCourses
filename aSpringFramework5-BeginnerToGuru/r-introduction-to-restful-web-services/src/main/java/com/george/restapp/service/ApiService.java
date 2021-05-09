package com.george.restapp.service;

import com.george.restapp.api.domain.User;

import java.util.List;

public interface ApiService {

    List<User> getUsers(Integer limit);
}
