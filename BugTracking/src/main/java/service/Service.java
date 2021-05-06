package service;

import domain.Bug;
import domain.BugStatus;
import domain.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.IBugRepository;
import repository.IProgrammerRepository;
import repository.ITesterRepository;

public class Service implements IService{
    private final IProgrammerRepository programmerRepository;
    private final ITesterRepository testerRepository;
    private final IBugRepository bugRepository;
    private static final Logger logger= LogManager.getLogger();
    public Service(IProgrammerRepository programmerRepository, ITesterRepository testerRepository, IBugRepository bugRepository) {
        this.programmerRepository = programmerRepository;
        this.testerRepository = testerRepository;
        this.bugRepository = bugRepository;
    }

    @Override
    public User login(String username, String password) {
        logger.traceEntry("Trying log in:",username);
        User user;
        user = programmerRepository.getProgrammer(username);
        if(user==null)
            user = testerRepository.getTester(username);
        if(user==null || !user.getPassword().equals(password))
            throw new ServiceException("Invalid Credentials!");
        logger.log(Level.INFO,"Logged in... " + username);
        return user;
    }

    @Override
    public void registerNewBug(String name, String description){
        logger.traceEntry("Saving bug {}",name);
        Bug bug = new Bug(name, description, BugStatus.UNSOLVED);
        bugRepository.save(bug);
        logger.traceExit();
    }

    @Override
    public Iterable<Bug> getAllBugs() {
        return bugRepository.getAll();
    }

    @Override
    public void shutdown() {
        programmerRepository.shutdown();
        testerRepository.shutdown();
    }
}
