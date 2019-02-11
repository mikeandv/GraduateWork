package dbhandler.dao;

import dbhandler.DBSessionFactory;
import entity.CookieSession;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CookieSessionDaoHib implements CookieSessionDao{
    @Override
    public void save(CookieSession cookieSession) {

        Session session = DBSessionFactory.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.save(cookieSession);
        tr.commit();
        session.close();
    }

    @Override
    public Set<CookieSession> findByUserIdAndCode(Long id, String code) {

        Session session = DBSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CookieSession> cr = cb.createQuery(CookieSession.class);
        Root<CookieSession> root = cr.from(CookieSession.class);

        Predicate userId = cb.equal(root.get("user"), id);
        Predicate statusCode = cb.equal(root.get("status"), code);
        cr.select(root).where(cb.and(userId, statusCode));

        Query<CookieSession> query = session.createQuery(cr);

        List<CookieSession> cookieSession;

        try{
            cookieSession = query.getResultList();
        } catch (NoResultException e) {
            cookieSession = null;
        }

        Set<CookieSession> tmp = new HashSet<>(cookieSession);
        session.close();

        return tmp;
    }

    @Override
    public void update(CookieSession cookieSession) {
        Session session = DBSessionFactory.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.update(cookieSession);
        tr.commit();
        session.close();
    }

    @Override
    public CookieSession findById(Long id) {
        return DBSessionFactory.getSessionFactory().openSession().get(CookieSession.class, id);
    }
}
