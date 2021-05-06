package repository.hibernate;

import domain.Programmer;
import domain.validators.ProgrammerValidator;
import domain.validators.ValidationException;
import junit.framework.TestCase;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmProgrammerRepoTest extends TestCase {

    private HbmProgrammerRepo repo;
    private SessionFactory session;
    private SessionFactory initialise() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernateMock.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        return meta.getSessionFactoryBuilder().build();
    }

    public void setUp() throws Exception {
        super.setUp();
        session = initialise();
        repo = new HbmProgrammerRepo(session,new ProgrammerValidator());
        for(Programmer p: repo.getAll())
            repo.delete(p.getId());
    }

    public void tearDown() throws Exception {
        for(Programmer p: repo.getAll())
            repo.delete(p.getId());
        session.close();
    }

    public void testCrud(){
        for (Programmer programmer : repo.getAll())
            assert false;
        Programmer p = new Programmer("test1","password1");
        Programmer p1 = new Programmer("test2","");
        Programmer p2 = new Programmer("","password3");
        Programmer p3 = new Programmer("","");
        repo.save(p);
        int i = 0;
        for (Programmer programmer : repo.getAll()){
            assertEquals(programmer.getUsername(),p.getUsername());
            assertEquals(programmer.getPassword(),p.getPassword());
            i++;
        }
        assertEquals(1,i);
        try {
            repo.save(null);
            assert false;
        }catch (ValidationException e){assertEquals("User can't be null!",e.getMessage());}
        try {
            repo.save(p1);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid password!",e.getMessage());}
        p1.setPassword(null);
        try {
            repo.save(p1);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid password!",e.getMessage());}
        try {
            repo.save(p2);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid username!\n",e.getMessage());}
        p2.setUsername(null);
        try {
            repo.save(p2);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid username!\n",e.getMessage());}
        try {
            repo.save(p3);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid username!\nInvalid password!",e.getMessage());}
        p3.setUsername(null);
        p3.setPassword(null);
        try {
            repo.save(p3);
            assert false;
        }catch (ValidationException e){assertEquals("Invalid username!\nInvalid password!",e.getMessage());}
        i = 0;
        for (Programmer programmer : repo.getAll()){
            assertEquals(programmer.getUsername(),p.getUsername());
            assertEquals(programmer.getPassword(),p.getPassword());
            p1 = programmer;
            i++;
        }
        assertEquals(1,i);
        p1 = repo.get(p1.getId());
        assertEquals(p.getUsername(),p1.getUsername());
        assertEquals(p.getPassword(),p1.getPassword());
        p1.setUsername("test2");
        p1.setPassword("test2");
        repo.save(p1);
        i = 0;
        for (Programmer programmer : repo.getAll()){
            if(programmer.getId().equals(p.getId())){
                assertEquals(programmer.getUsername(),p.getUsername());
                assertEquals(programmer.getPassword(),p.getPassword());
            }
            else{
                assertEquals(programmer.getUsername(),p1.getUsername());
                assertEquals(programmer.getPassword(),p1.getPassword());
            }
            i++;
        }
        assertEquals(2,i);
        repo.delete(p1.getId());
        i = 0;
        for (Programmer programmer : repo.getAll()){
            assertEquals(programmer.getUsername(),p.getUsername());
            assertEquals(programmer.getPassword(),p.getPassword());
            i++;
        }
        assertEquals(1,i);
        p1 = repo.get(p.getId());
        assertEquals(p1.getUsername(),p.getUsername());
        assertEquals(p1.getId(),p.getId());
        assertEquals(p1.getPassword(),p.getPassword());
        p.setUsername("Update");
        repo.update(p);
        p = repo.get(p.getId());
        assertEquals("Update",p.getUsername());
        assertEquals(p1.getId(),p.getId());
        assertEquals(p1.getPassword(),p.getPassword());
    }

    public void testExtra(){
        Programmer p = new Programmer("test1","test1");
        Programmer p2 = new Programmer("test2","test2");
        repo.save(p);
        repo.save(p2);
        assertNull(repo.getProgrammer(null));
        assertNull(repo.getProgrammer(""));
        assertNull(repo.getProgrammer("NonExistingUsername"));
        assertEquals(p.getUsername(),repo.getProgrammer(p.getUsername()).getUsername());
        assertEquals(p2.getUsername(),repo.getProgrammer(p2.getUsername()).getUsername());
    }

}