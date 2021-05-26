package service;

import domain.*;

public interface IService extends IObservable {
    User login(String username, String password);
    void registerNewBug(String name, String description);
    Iterable<Bug> getAllBugs();
    void shutdown();
    void registerToBug(Bug bug, Programmer programmer);
    Iterable<Bug> getBugs(Programmer user);
    void markAsSolved(Bug bug, Programmer programmer);
    void modifyStatus(Bug bug, BugStatus status, Tester tester);
}
