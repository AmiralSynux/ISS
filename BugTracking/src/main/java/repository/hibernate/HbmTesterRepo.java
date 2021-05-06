package repository.hibernate;

import domain.Tester;
import domain.validators.Validator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.ITesterRepository;

import java.util.List;

public class HbmTesterRepo extends HbmRepo<Long, Tester> implements ITesterRepository {
    public HbmTesterRepo(SessionFactory sessionFactory, Validator<Tester> validator) {
        super(sessionFactory, validator);
    }

    @Override
    public Tester getTester(String username) {
        logger.traceEntry(username);
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Tester tester = session.createQuery("from Tester where username = :tid", Tester.class).setParameter("tid",username).uniqueResult();
                tx.commit();
                logger.traceExit("Exiting with: " + tester);
                return tester;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        logger.traceExit("Nothing found");
        return null;
    }

//    @Override
//    protected Tester deleteQuery(Session session, Long aLong) {
//        return session.createQuery("from Tester where id=:tid")
//    }

    @Override
    protected Tester getQuery(Session session, Long aLong) {
        return  session.createQuery("from Tester where id = :tid",
                Tester.class).setParameter("tid",aLong).uniqueResult();
    }

    @Override
    protected List<Tester> getAllQuery(Session session) {
        return session.createQuery("from Tester as m order by m.username asc",
                Tester.class).list();
    }
}
