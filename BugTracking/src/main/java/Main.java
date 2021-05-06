import domain.validators.BugValidator;
import domain.validators.ProgrammerValidator;
import domain.validators.TesterValidator;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.IBugRepository;
import repository.IProgrammerRepository;
import repository.ITesterRepository;
import repository.hibernate.HbmBugRepo;
import repository.hibernate.HbmProgrammerRepo;
import repository.hibernate.HbmTesterRepo;
import service.IService;
import service.Service;
import ui.stages.LoginStage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        IService service = getService();
        primaryStage = new LoginStage(service);
        primaryStage.show();

    }

    private SessionFactory initialise() {
        StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
        Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
        return meta.getSessionFactoryBuilder().build();
    }

    private IService getService() {
        SessionFactory sessionFactory = initialise();
        IProgrammerRepository pRepo = new HbmProgrammerRepo(sessionFactory,new ProgrammerValidator());
        ITesterRepository tRepo = new HbmTesterRepo(sessionFactory, new TesterValidator());
        IBugRepository bRepo = new HbmBugRepo(sessionFactory, new BugValidator());
        return new Service(pRepo,tRepo, bRepo);
    }
}
