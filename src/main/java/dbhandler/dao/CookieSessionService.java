package dbhandler.dao;

import entity.CookieSession;

import java.util.Set;

public class CookieSessionService {
    private CookieSessionDao cookieSessionDao = new CookieSessionDaoHib();

    public CookieSessionService(){}

    public void saveCookieSession(CookieSession cookieSession) {
        cookieSessionDao.save(cookieSession);
    }

    public Set<CookieSession> findCookiSessByUserIdAndCode(Long id, String code) {
        return cookieSessionDao.findByUserIdAndCode(id, code);
    }
}
