package domain;

public class Programmer extends User {
    public Programmer() {
    }

    public Programmer(String username, String password) {
        super(username, password);
    }

    @Override
    public String toString() {
        return "Programmer" + super.toString();
    }
}
