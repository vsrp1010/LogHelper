package io.vsrp.domain;

import java.util.ArrayList;
import java.util.List;

public class Iteration {

    private String id;
    List<LogEvent> events = new ArrayList();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LogEvent> getEvents() {
        return events;
    }

    public void setEvents(List<LogEvent> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return "Iteration{" +
                "id='" + id + '\'' +
                ", eventCount=" + events.size() +
                ", events=" + events +
                '}';
    }
}
