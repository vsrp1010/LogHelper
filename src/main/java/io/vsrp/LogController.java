package io.vsrp;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;

@RestController
public class LogController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/parse")
    public String parse(@PathParam("LogDir") String logDir) {
        return "Parsing logs from directory: " + logDir;
    }
}
