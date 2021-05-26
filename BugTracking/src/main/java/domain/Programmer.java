package domain;

import java.util.*;

public class Programmer extends User {
    public Programmer() {
    }

    public Programmer(String username, String password) {
        super(username, password);
        bugs = new HashSet<>();
    }

    private Set<Bug> bugs;

    public void addBug(Bug bug){
        bugs.add(bug);
    }

    public Set<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(Set<Bug> bugs) {
        this.bugs = bugs;
    }

    @Override
    public String toString() {
        return "Programmer" + super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Programmer))return false;
        Programmer p = (Programmer)obj;
        return this.getUsername().equals(p.getUsername()) && this.getId().equals(p.getId());
    }
}
