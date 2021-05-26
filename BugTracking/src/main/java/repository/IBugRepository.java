package repository;

import domain.Bug;


public interface IBugRepository extends IRepository<Long, Bug> {
    Iterable<Bug> getAllWithoutSolved();
}
