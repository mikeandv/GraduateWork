package dbhandler.dao;

import entity.CookieSession;
import entity.User;

import java.util.List;
import java.util.Set;

public class UserService {
    private UserDao userDao = new UserDaoHib();

    public UserService(){

    }

    public User findUser(Long id) {
        return userDao.findById(id);
    }
    public User findUser(String email) {
        return userDao.findByEmail(email);
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(User user) {
        userDao.delete(user);
    }

    public Set<CookieSession> findCookieSessionByUserId(Long id) {
        return userDao.findCookieSessionByUserId(id);
    }
    public Set<CookieSession> findCookieSessionByUserIdAndCode(Long userId, String code) {
        return userDao.findCookieSessionByUserIdAndCode(userId, code);
    }

    public List<User> findAllUser() {
        return userDao.findAll();
    }

}
