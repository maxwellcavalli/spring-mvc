package br.com.fh.exampleorder.service;

import br.com.fh.exampleorder.model.user.Login;
import br.com.fh.exampleorder.model.user.User;

import java.util.List;

public interface UserService {

    User validateUser(Login login);

    User register(User user);

    User load(Integer id);

    List<User> findAll();

    void delete(Integer id);
}

