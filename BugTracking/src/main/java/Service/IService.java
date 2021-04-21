package Service;

import Domain.User;

public interface IService {
    User login(String username, String password);
}
