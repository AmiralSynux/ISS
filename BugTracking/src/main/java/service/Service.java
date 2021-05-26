package service;

import domain.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.IBugRepository;
import repository.IProgrammerRepository;
import repository.ITesterRepository;

import java.util.ArrayList;
import java.util.List;

public class Service implements IService {
    private final IProgrammerRepository programmerRepository;
    private final ITesterRepository testerRepository;
    private final IBugRepository bugRepository;
    private static final Logger logger= LogManager.getLogger();
    private List<IObserver> observers;
    public Service(IProgrammerRepository programmerRepository, ITesterRepository testerRepository, IBugRepository bugRepository) {
        this.programmerRepository = programmerRepository;
        this.testerRepository = testerRepository;
        this.bugRepository = bugRepository;
        observers = new ArrayList<>();
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
        notifyObs();
    }

    @Override
    public Iterable<Bug> getAllBugs() {
        return bugRepository.getAllWithoutSolved();
    }

    @Override
    public void shutdown() {
        programmerRepository.shutdown();
        testerRepository.shutdown();
        bugRepository.shutdown();
        observers.clear();
    }

    @Override
    public void registerToBug(Bug bug, Programmer programmer) {
        logger.traceEntry("Register to bug {0} with {1}",bug.getName(),programmer.getUsername());
        for(Programmer p : bug.getProgrammers())
            if(p.equals(programmer))
                throw new ServiceException("You are already registered to this bug!");
        programmer.addBug(bug);
        bug.addProgrammer(programmer);
        programmerRepository.update(programmer);
        bugRepository.update(bug);
        logger.traceExit();
        notifyObs();
    }

    @Override
    public Iterable<Bug> getBugs(Programmer user) {
        return programmerRepository.get(user.getId()).getBugs();
    }

    @Override
    public void markAsSolved(Bug bug, Programmer programmer) {
        logger.traceEntry("Bug {0}, Programmer {1}",bug.getName(),programmer.getUsername());
        boolean ok = false;
        for(Programmer p : bug.getProgrammers())
            if(p.equals(programmer))
            {ok = true;break;}
        if(!ok) {
            logger.error("Permission denied");
            throw new ServiceException("Permission denied!");
        }
        if(bug.getStatus() == BugStatus.UNSOLVED){
            bug.setStatus(BugStatus.WAITING_VALIDATION);
            bugRepository.update(bug);
            logger.traceExit();
            notifyObs();
        }
        else {
            logger.error("The bug you are trying to mark is not unsolved!");
            throw new ServiceException("The bug you are trying to mark is not unsolved!");
        }
    }

    @Override
    public void modifyStatus(Bug bug, BugStatus status, Tester tester) {
        logger.traceEntry("Bug {0}, Status {1}, Tester {2}",bug.getName(),status,tester.getUsername());
        if(bug.getStatus() != BugStatus.WAITING_VALIDATION)throw new ServiceException("Bug is not waiting validation!");
        if(testerRepository.get(tester.getId())==null)throw new ServiceException("Permission denied!");
        bug.setStatus(status);
        bugRepository.update(bug);
        logger.traceExit();
        notifyObs();
    }

    @Override
    public void attach(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObs() {
        observers.forEach(IObserver::update);
    }
}
