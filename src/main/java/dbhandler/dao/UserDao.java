package dbhandler.dao;

import entity.CookieSession;
import entity.User;

import java.util.List;
import java.util.Set;

public interface UserDao {

    User findById(Long id);
    User findByEmail(String email);
    void save(User user);
    void update(User user);
    void delete(User user);
    Set<CookieSession> findCookieSessionByUserId(Long userId);
    Set<CookieSession> findCookieSessionByUserIdAndCode(Long userId, String code);
    List<User> findAll();

}
