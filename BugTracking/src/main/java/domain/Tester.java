package domain;

public class Tester extends User{
    public Tester(){}

    public Tester(String username, String password) {
        super(username, password);
    }

    @Override
    public String toString() {
        return "Tester" + super.toString();
    }
}
