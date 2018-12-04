package io.vsrp.domain;

import java.util.ArrayList;
import java.util.List;

public class Report {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    private List<Job> jobs = new ArrayList();

    private List<LogEvent> events = new ArrayList();

    public List<LogEvent> getEvents() {
        return events;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    @Override
    public String toString() {
        StringBuilder toReturn = new StringBuilder(getId());
        this.getEvents().forEach(logEvent -> toReturn.append("\n").append(logEvent));
        return toReturn.toString();
    }

    public Report merge(Report report2) {
        report2.getEvents().forEach(job -> mergeJob(job));
        return this;
    }

    private void mergeJob(LogEvent event) {
        this.getEvents().add(event);
    }

    public void sort() {
        this.getEvents().sort(
                (o1, o2) ->  {
                    if(o1.getJob().compareTo(o2.getJob()) !=0) {
                        return o1.getJob().compareTo(o2.getJob());
                    } else if(o1.getRunnable().compareTo(o2.getRunnable()) !=0) {
                        return o1.getRunnable().compareTo(o2.getRunnable());
                    } else if(o1.getIteration().compareTo(o2.getIteration()) !=0) {
                        return o1.getIteration().compareTo(o2.getIteration());
                    }
                    return Long.valueOf(o1.getTimestamp()).compareTo(Long.valueOf(o2.getTimestamp()));
                }
        );
    }
}
