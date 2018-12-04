package io.vsrp.domain;

import java.util.ArrayList;
import java.util.List;

public class Job {
    
    private String team;
    private String name;
    private List<LogEvent> events;
    private List<io.vsrp.domain.Runnable> runnables = new ArrayList();

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Runnable> getRunnables() {
        return runnables;
    }

    @Override
    public String toString() {
        return "Job{" +
                "team='" + team + '\'' +
                ", name='" + name + '\'' +
                ", runnables=" + this.runnables.size() +
                ", runnables=" + this.runnables +
                '}';
    }
}
