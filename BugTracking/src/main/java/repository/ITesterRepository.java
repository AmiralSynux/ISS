package repository;

import domain.Tester;

public interface ITesterRepository extends IRepository<Long, Tester> {
    Tester getTester(String username);

}
