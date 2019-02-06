package dbhandler.dao;

import dbhandler.DBSessionFactory;
import entity.ServiceLog;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServiceLogDaoHib implements ServiceLogDao{

    public void save(ServiceLog serviceLog) {
        Session session = DBSessionFactory.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.save(serviceLog);
        tr.commit();
        session.close();
    }
}
