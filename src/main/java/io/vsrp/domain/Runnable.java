package io.vsrp.domain;

import java.util.ArrayList;
import java.util.List;

public class Runnable {

    private String name;
    private List<LogEvent> events;
    private List<Iteration> iterations = new ArrayList();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Iteration> getIterations() {
        return iterations;
    }

    @Override
    public String toString() {
        return "Runnable{" +
                "name='" + name + '\'' +
                ", itrCount=" + iterations.size() +
                ", iterations=" + iterations +
                '}';
    }
}
