package br.com.fh.exampleorder.service.impl;

import br.com.fh.exampleorder.dao.UserDao;
import br.com.fh.exampleorder.model.user.Login;
import br.com.fh.exampleorder.model.user.User;
import br.com.fh.exampleorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultUserService implements UserService {

    @Autowired
    private UserDao userDao;


    public User validateUser(Login login) {
        return userDao.validateUser(login);
    }

    public User register(User user) {
        if (user.getId() == null || user.getId() == 0) {
            return userDao.register(user);
        } else {
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                User oldUser = load(user.getId());

                user.setUsername(oldUser.getUsername());
                user.setPassword(oldUser.getPassword());
            }

            userDao.update(user);
            return user;
        }
    }

    @Override
    public User load(Integer id) {
        return userDao.load(id);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void delete(Integer id) {
        userDao.delete(id);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
