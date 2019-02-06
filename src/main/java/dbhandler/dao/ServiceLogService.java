package dbhandler.dao;

import entity.ServiceLog;

public class ServiceLogService {
    ServiceLogDao serviceLogDao = new ServiceLogDaoHib();

    public void save(ServiceLog serviceLog) {
        serviceLogDao.save(serviceLog);
    }
}
