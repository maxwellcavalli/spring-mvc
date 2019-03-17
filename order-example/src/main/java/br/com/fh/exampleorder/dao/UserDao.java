package br.com.fh.exampleorder.dao;

import br.com.fh.exampleorder.model.user.Login;
import br.com.fh.exampleorder.model.user.User;

import java.util.List;

public interface UserDao {
    User register(User user);

    void update(User user);

    void delete(Integer id);

    User load(Integer id);

    List<User> findAll();

    User validateUser(Login login);
}
