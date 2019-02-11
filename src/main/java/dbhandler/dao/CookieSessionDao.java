package dbhandler.dao;

import entity.CookieSession;

import java.util.Set;

public interface CookieSessionDao {
    void save(CookieSession cookieSession);
    Set<CookieSession> findByUserIdAndCode(Long id, String Code);
    void update(CookieSession cookieSession);
    CookieSession findById(Long id);
}
