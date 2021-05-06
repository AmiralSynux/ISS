package repository;

import domain.Entity;

public interface IRepository<ID, E extends Entity<ID>> {
    void save(E entity);
    void delete(ID id);
    E get(ID id);
    Iterable<E> getAll();
    void update(E entity);
    void shutdown();
}
