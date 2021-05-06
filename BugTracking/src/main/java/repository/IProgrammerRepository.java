package repository;

import domain.Programmer;

public interface IProgrammerRepository extends IRepository<Long, Programmer> {
    Programmer getProgrammer(String username);
}
