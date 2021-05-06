package repository.hibernate;

import domain.Bug;
import domain.BugStatus;
import domain.validators.BugValidator;
import domain.validators.BugValidator;
import domain.validators.ValidationException;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmBugRepoTest extends TestCase {

    private HbmBugRepo repo;
    private SessionFactory session;
    private SessionFactory initialise() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernateMock.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        return meta.getSessionFactoryBuilder().build();
    }

    public void setUp() throws Exception {
        super.setUp();
        session = initialise();
        repo = new HbmBugRepo(session,new BugValidator());
        for(Bug b: repo.getAll())
            repo.delete(b.getId());
    }

    public void tearDown() throws Exception {
        for(Bug b: repo.getAll())
            repo.delete(b.getId());
        session.close();
    }

    public void testCrud(){
        for (Bug bug : repo.getAll())
            assert false;
        Bug b = new Bug("name","description", BugStatus.UNSOLVED);
        Bug b2 = new Bug("name","description", BugStatus.SOLVED);
        Bug b3 = new Bug("name","description", BugStatus.WAITING_VALIDATION);
        repo.save(b);
        repo.save(b2);
        repo.save(b3);
        int i = 0;
        for (Bug bug : repo.getAll()){
            if(bug.getId().equals(b.getId())){
                assertEquals(bug.getName(),b.getName());
                assertEquals(bug.getDescription(),b.getDescription());
                assertEquals(bug.getStatus(),b.getStatus());
            }
            else  if(bug.getId().equals(b2.getId())){
                assertEquals(bug.getName(),b2.getName());
                assertEquals(bug.getDescription(),b2.getDescription());
                assertEquals(bug.getStatus(),b2.getStatus());
            }
            else {
                assertEquals(bug.getId(),b3.getId());
                assertEquals(bug.getName(),b3.getName());
                assertEquals(bug.getDescription(),b3.getDescription());
                assertEquals(bug.getStatus(),b3.getStatus());
            }
            i++;
        }
        assertEquals(3,i);
        try {
            repo.save(null);
            assert false;
        }catch (ValidationException e){assertEquals("Bug can't be null!",e.getMessage());}
        Bug b4 = new Bug("","description",BugStatus.UNSOLVED);
        try {
            repo.save(b4);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid name!\n",e.getMessage());}
        b4.setName(null);
        try {
            repo.save(b4);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid name!\n",e.getMessage());}
        b4.setName("name");
        b4.setDescription("");
        try {
            repo.save(b4);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid description!\n",e.getMessage());}
        b4.setDescription(null);
        try {
            repo.save(b4);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid description!\n",e.getMessage());}
        b4.setDescription("description");
        b4.setStatus(null);
        try {
            repo.save(b4);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid status!",e.getMessage());}
        b4.setName("");
        b4.setDescription(null);
        try {
            repo.save(b4);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid name!\nInvalid description!\nInvalid status!",e.getMessage());}

        i = 0;
        for (Bug bug : repo.getAll()){
            if(bug.getId().equals(b.getId())){
                assertEquals(bug.getName(),b.getName());
                assertEquals(bug.getDescription(),b.getDescription());
                assertEquals(bug.getStatus(),b.getStatus());
            }
            else  if(bug.getId().equals(b2.getId())){
                assertEquals(bug.getName(),b2.getName());
                assertEquals(bug.getDescription(),b2.getDescription());
                assertEquals(bug.getStatus(),b2.getStatus());
            }
            else {
                assertEquals(bug.getId(),b3.getId());
                assertEquals(bug.getName(),b3.getName());
                assertEquals(bug.getDescription(),b3.getDescription());
                assertEquals(bug.getStatus(),b3.getStatus());
            }
            i++;
        }
        assertEquals(3,i);
        b4 = repo.get(b.getId());
        assertEquals(b4.getName(),b.getName());
        assertEquals(b4.getDescription(),b.getDescription());
        assertEquals(b4.getStatus(),b.getStatus());
        b4 = repo.get(b2.getId());
        assertEquals(b4.getName(),b2.getName());
        assertEquals(b4.getDescription(),b2.getDescription());
        assertEquals(b4.getStatus(),b2.getStatus());
        b4 = repo.get(b3.getId());
        assertEquals(b4.getName(),b3.getName());
        assertEquals(b4.getDescription(),b3.getDescription());
        assertEquals(b4.getStatus(),b3.getStatus());
        b3.setStatus(null);
        try {
            repo.update(b3);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid status!",e.getMessage());}
        b3 = repo.get(b3.getId());
        assertEquals(b4.getName(),b3.getName());
        assertEquals(b4.getDescription(),b3.getDescription());
        assertEquals(b4.getStatus(),b3.getStatus());
        b3.setStatus(BugStatus.SOLVED);
        repo.update(b3);
        b3 = repo.get(b3.getId());
        assertEquals(b3.getStatus(),BugStatus.SOLVED);
        assertEquals(b4.getName(),b3.getName());
        assertEquals(b4.getDescription(),b3.getDescription());
        i = 0;
        for (Bug bug : repo.getAll()){
            if(bug.getId().equals(b.getId())){
                assertEquals(bug.getName(),b.getName());
                assertEquals(bug.getDescription(),b.getDescription());
                assertEquals(bug.getStatus(),b.getStatus());
            }
            else  if(bug.getId().equals(b2.getId())){
                assertEquals(bug.getName(),b2.getName());
                assertEquals(bug.getDescription(),b2.getDescription());
                assertEquals(bug.getStatus(),b2.getStatus());
            }
            else {
                assertEquals(bug.getId(),b3.getId());
                assertEquals(bug.getName(),b3.getName());
                assertEquals(bug.getDescription(),b3.getDescription());
                assertEquals(bug.getStatus(),b3.getStatus());
            }
            i++;
        }
        assertEquals(3,i);
        repo.delete(b.getId());
        i = 0;
        for (Bug bug : repo.getAll()){
            if(bug.getId().equals(b2.getId())){
                assertEquals(bug.getName(),b2.getName());
                assertEquals(bug.getDescription(),b2.getDescription());
                assertEquals(bug.getStatus(),b2.getStatus());
            }
            else {
                assertEquals(bug.getId(),b3.getId());
                assertEquals(bug.getName(),b3.getName());
                assertEquals(bug.getDescription(),b3.getDescription());
                assertEquals(bug.getStatus(),b3.getStatus());
            }
            i++;
        }
        assertEquals(2,i);
    }
}