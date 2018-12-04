package io.vsrp;

import io.vsrp.domain.LogEvent;
import io.vsrp.domain.Report;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class FileParser {

    private String logDir;
    private String jobFilter;
    private String runnableFilter;
    private String iterationFilter;
    
    public FileParser(String filename) {
        this.logDir = filename;
    }
    
    public String getLogDir() {
        return logDir;
    }

    public FileParser withJob(String job) {
        this.jobFilter = job;
        return this;
    }

    public FileParser withRunnable(String runnable) {
        this.runnableFilter = runnable;
        return this;
    }

    public FileParser withIteration(String iteration) {
        this.iterationFilter = iteration;
        return this;
    }

    /*
     * want something like this:
     * Start.scanDir(dirPath)   // stream of filenames
     *      .map(file -> parseFile)  // for each file, parse and create report object
     *      .collect(allLines)  // collect the "record" created from each line
     *      .sort(sortedResult) // returns sorted result, sorted as per timestamp
     *      
     */
    public Report parse(String jobName, String runnableName) throws IOException {
        Report finalReport = new Report();
        finalReport.setId("Event log report generated on " + new Date(System.currentTimeMillis()).toString());

        DirectoryStream.Filter<Path> notDirectory = entry -> !Files.isDirectory(entry);

        Spliterator<Path> paths = Files.newDirectoryStream(Paths.get(this.logDir), f -> !Files.isDirectory(f)).spliterator();
        StreamSupport.stream(paths, false)
                .map(path -> {
                    System.out.println(path);
                    return path;
                })
                .flatMap(FileParser::lines)
                .map(String::trim)
                .filter(s -> s.split("\t").length > 20)
                .filter(s -> jobFilter == null || s.contains("jobName=" + jobFilter))
                .filter(s -> runnableFilter == null || s.contains("runnableName=" + runnableFilter))
                .filter(s -> iterationFilter == null || s.contains("iterationVersion=" + iterationFilter))
                .map(FileParser::parseLine)
                .reduce(finalReport, this::mergeReport);


//        Files.newDirectoryStream(Paths.get("logs"),f ->  !Files.isDirectory(f))
//                .forEach( file -> {
//                    try {
//                        System.out.println("Got file: " + file);
//                        Files.lines(file)
//                                .map(String::trim)
//                                .filter(s -> s.split("\t").length > 20)
//                                .filter(s -> jobFilter == null || s.contains("jobName=" + jobFilter))
//                                .filter(s -> (runnableFilter != null) ? s.contains("runnableName="+runnableFilter): true)
//                                .filter(s -> (iterationFilter != null) ? s.contains("iterationVersion="+iterationFilter): true)
//                                .map(this::parseLine)
//                                .reduce(finalReport, this::mergeReport);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
        finalReport.sort();
        System.out.println(finalReport);
        return null;
    }

    public static Stream<String> lines(Path path) {
        try {
            return Files.lines(path);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Report mergeReport(Report report, Report report2) {
        return report.merge(report2);
    }

    public static Report parseLine(String s) {
        System.err.println(s);
        String[] tokens = s.split("\t");
        String rawMessage = tokens[tokens.length-1].split("applog.msg=")[1];
        Report rp = new Report();
        LogEvent event = new LogEvent();
        if(rawMessage.contains("iterationVersion=")) {
            event.setIteration(rawMessage.split("iterationVersion=")[1].split(",")[0]);
        }
        event.setJob(rawMessage.split("jobName=")[1].split(",")[0]);
        event.setRunnable(rawMessage.split("runnableName=")[1].split(",")[0]);
        event.setTimestamp(Long.parseLong(tokens[1]));
        event.setServerHost(tokens[3]);
        event.setMessage(rawMessage.split(",")[0]);
        if(rawMessage.contains(",iterationStatus=")) {
            event.setStatus(rawMessage.split(",iterationStatus=")[1].split(",")[0]);
        }
        if(rawMessage.contains(",iterationSlaStatus=")) {
            event.setSlaStatus(rawMessage.split(",iterationSlaStatus=")[1].split(",")[0]);
        }
        rp.getEvents().add(event);
        return rp;
    }

    static Consumer<String> processLine = l -> System.out.println(l+"@@");

    static Function<String, String> expandFunction = (l) -> {
      return l+"..";  
    };
    
    public static void main(String ... args) throws IOException {
        String logsDir = "logs";
        String job = "BatchPayloadMonitor";
        String runnable = "BatchPayloadMonitor2";
        String iteration = null;
        FileParser fp = new FileParser(logsDir).withJob(job)
                                .withRunnable(runnable)
                                .withIteration(iteration);
        long startTime = System.currentTimeMillis();
        Report report = fp.parse(job,runnable);
        System.out.println("Job completed in time = " + (System.currentTimeMillis() - startTime));
//        System.out.println(fp.expandFunction.apply("hell"));
    }
}
