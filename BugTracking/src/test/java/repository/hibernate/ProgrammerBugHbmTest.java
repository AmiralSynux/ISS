package repository.hibernate;

import domain.Bug;
import domain.BugStatus;
import domain.Programmer;
import domain.validators.BugValidator;
import domain.validators.ProgrammerValidator;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ProgrammerBugHbmTest extends TestCase {

    private HbmProgrammerRepo programmerRepo;
    private HbmBugRepo bugRepo;
    private SessionFactory session;
    private SessionFactory initialise() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernateMock.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        return meta.getSessionFactoryBuilder().build();
    }

    public void setUp() throws Exception {
        super.setUp();
        session = initialise();
        bugRepo = new HbmBugRepo(session,new BugValidator());
        for(Bug b: bugRepo.getAll())
            bugRepo.delete(b.getId());
        programmerRepo = new HbmProgrammerRepo(session,new ProgrammerValidator());
        for(Programmer p: programmerRepo.getAll())
            programmerRepo.delete(p.getId());
    }

    public void tearDown() throws Exception {
        for(Bug b: bugRepo.getAll())
            bugRepo.delete(b.getId());
        for(Programmer p: programmerRepo.getAll())
            programmerRepo.delete(p.getId());
        session.close();
    }

    public void testBugProgrammer() {
        Bug b = new Bug("name","description", BugStatus.UNSOLVED);
        Programmer p = new Programmer("test1","password1");
        b = bugRepo.save(b);
        p = programmerRepo.save(p);
        p.addBug(b);
        bugRepo.update(b);
        b = bugRepo.get(b.getId());
        for(Programmer pr : b.getProgrammers())
        {
            assertEquals(p.getUsername(),pr.getUsername());
            assertEquals(p.getPassword(),pr.getPassword());
            assertEquals(p.getId(),pr.getId());
        }
    }
}