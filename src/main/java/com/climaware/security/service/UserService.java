package com.climaware.security.service;

import com.climaware.persistence.SystemDataAccess;
import com.climaware.security.model.User;

import java.util.List;

public class UserService {

    public User get(long id) {
        return (User) SystemDataAccess.get(User.class, id);
    }

    public List<User> getAll() {
        return SystemDataAccess.getAll("select p from User p ");
    }

    public void add(User user) {
        user = encryptuser(user);
        user.setId(null);
        SystemDataAccess.add(user);
    }

    public void delete(long id) {
        SystemDataAccess.delete(User.class, id);
    }

    public User set(long id, User user) {
        if (!doesExist(id))
            return null;

        return (User) SystemDataAccess.set(User.class, id, user);
    }

    private boolean doesExist(long id) {
        Object object = get(id);
        return object != null;
    }

    public User getByUsername(String username) {
        Object[][] tvoObject = new Object[1][2];
        tvoObject[0][0] = "username";
        tvoObject[0][1] = username;
        List<User> ppAll = SystemDataAccess.getWithParams("select p from User  p where p.username in (:username) ", tvoObject);
        if (ppAll.size() > 0) {
            return ppAll.get(0);
        }
        return null;
    }

    public User encryptuser(User user) {
        String password = Encryptor.generateStrongPasswordHash(user.getPassword());
        user.setPassword(password);
        return user;
    }

}
