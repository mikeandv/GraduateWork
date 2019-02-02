package dbhandler.dao;

import dbhandler.DBSessionFactory;
import entity.CookieSession;
import entity.User;
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

public class UserDaoHib implements UserDao {
    @Override
    public User findById(Long id) {
        return DBSessionFactory.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public User findByEmail(String email) {
        Session session = DBSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = cb.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root).where(cb.equal(root.get("email"), email));

        Query<User> query = session.createQuery(cr);

        User user;

        try{
            user = query.getSingleResult();
        } catch (NoResultException e) {
            user = null;
        }
        session.close();
        return user;
    }

    @Override
    public void save(User user) {
        Session session = DBSessionFactory.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.save(user);
        tr.commit();
        session.close();
    }

    @Override
    public void update(User user) {
        Session session = DBSessionFactory.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.update(user);
        tr.commit();
        session.close();

    }

    @Override
    public void delete(User user) {
        Session session = DBSessionFactory.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.delete(user);
        tr.commit();
        session.close();
    }

    @Override
    public Set<CookieSession> findCookieSessionByUserId(Long userId) {

        Session session = DBSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CookieSession> cr = cb.createQuery(CookieSession.class);
        Root<CookieSession> root = cr.from(CookieSession.class);
        cr.select(root).where(cb.equal(root.get("user"), userId));

        Query<CookieSession> query = session.createQuery(cr);

        Set<CookieSession> tmp = new HashSet<>(query.getResultList());
        session.close();

        return tmp;
    }
    public Set<CookieSession> findCookieSessionByUserIdAndCode(Long userId, String code) {
        Session session = DBSessionFactory.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CookieSession> cr = cb.createQuery(CookieSession.class);
        Root<CookieSession> root = cr.from(CookieSession.class);

        Predicate cookieUserId = cb.equal(root.get("user"), userId);
        Predicate statusCode = cb.equal(root.get("status"), code);
        cr.select(root).where(cb.and(cookieUserId, statusCode));

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
    public List<User> findAll() {
        return DBSessionFactory.getSessionFactory().openSession().createQuery("From User").list();
    }
}
