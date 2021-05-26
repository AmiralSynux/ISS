package domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bug extends Entity<Long>{
    private String name;
    private String description;
    private BugStatus status;
    private Set<Programmer> programmers;

    public Bug(){}

    public void addProgrammer(Programmer p){programmers.add(p);}

    public Bug(String name, String description, BugStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
        programmers = new HashSet<>();
    }

    public Set<Programmer> getProgrammers() {
        return programmers;
    }

    public void setProgrammers(Set<Programmer> programmers) {
        this.programmers = programmers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
