package dbhandler.dao;

import dbhandler.DBSessionFactory;
import entity.CookieSession;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
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

        return query.getSingleResult();
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

        return new HashSet<>(query.getResultList());
    }

    @Override
    public List<User> findAll() {
        return DBSessionFactory.getSessionFactory().openSession().createQuery("From User").list();
    }
}
