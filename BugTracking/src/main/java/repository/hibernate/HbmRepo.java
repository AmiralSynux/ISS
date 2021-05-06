package repository.hibernate;

import domain.Entity;
import domain.validators.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class HbmRepo<ID, E extends Entity<ID>> implements IRepository<ID,E> {
    protected static SessionFactory sessionFactory;
    protected final Validator<E> validator;
    protected static final Logger logger= LogManager.getLogger();
    public HbmRepo(SessionFactory sessionFactory, Validator<E> validator) {
        this.validator = validator;
        HbmRepo.sessionFactory = sessionFactory;
    }

    @Override
    public void shutdown() {
        if(sessionFactory.isOpen())
            sessionFactory.close();
    }

    //    protected abstract E deleteQuery(Session session, ID id);
    protected abstract E getQuery(Session session, ID id);
    protected abstract List<E> getAllQuery(Session session);

    @Override
    public void save(E entity) {
        logger.traceEntry("{}",entity);
        validator.validate(entity);
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(entity);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        logger.traceExit();
    }
    @Override
    public void delete(ID id) {
        logger.traceEntry();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                E entity = getQuery(session,id);
                session.delete(entity);
                tx.commit();
                logger.traceExit("Deleted successfully");
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    @Override
    public E get(ID id) {
        logger.traceEntry();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                E entity = getQuery(session,id);
                tx.commit();
                logger.traceExit("Exiting with: " + entity);
                return entity;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        logger.traceExit("Nothing found");
        return null;
    }

    @Override
    public Iterable<E> getAll() {
        logger.traceEntry();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<E> entities = getAllQuery(session);
                tx.commit();
                logger.traceExit("Exiting with " + entities.size());
                return entities;
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
        logger.traceExit("Nothing found");
        return new ArrayList<>();
    }

    @Override
    public void update(E entity) {
        logger.traceEntry("{}",entity);
        validator.validate(entity);
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                session.update(entity);
                tx.commit();
            } catch(RuntimeException ex){
                if (tx!=null)
                    tx.rollback();
            }
        }
        logger.traceExit();
    }

}
