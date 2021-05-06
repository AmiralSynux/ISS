package repository.hibernate;

import domain.Programmer;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.IProgrammerRepository;

import java.util.List;

public class HbmProgrammerRepo extends HbmRepo<Long,Programmer> implements IProgrammerRepository {
    public HbmProgrammerRepo(SessionFactory sessionFactory, Validator<Programmer> validator) {
        super(sessionFactory, validator);
    }

//    @Override
//    protected Programmer deleteQuery(Session session, Long aLong) {
//        return session.createQuery("from Programmer where id = :tid",
//                Programmer.class).setParameter("tid",aLong).uniqueResult();
//    }

    @Override
    protected Programmer getQuery(Session session, Long aLong) {
        return  session.createQuery("from Programmer where id = :tid",
                Programmer.class).setParameter("tid",aLong).uniqueResult();
    }

    @Override
    protected List<Programmer> getAllQuery(Session session) {
        return session.createQuery("from Programmer as m order by m.username asc",
                Programmer.class).list();
    }
    @Override
    public Programmer getProgrammer(String username) {
        logger.traceEntry(username);
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Programmer programmer = session.createQuery("from Programmer where username = :tid", Programmer.class).setParameter("tid",username).uniqueResult();
                tx.commit();
                logger.traceExit("Exiting with: " + programmer);
                return programmer;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        logger.traceExit("Nothing found");
        return null;
    }
}
