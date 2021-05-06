package service;

import domain.Bug;
import domain.User;

public interface IService {
    User login(String username, String password);
    void registerNewBug(String name, String description);
    Iterable<Bug> getAllBugs();
    void shutdown();

}
