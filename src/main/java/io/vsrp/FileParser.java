package io.vsrp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.function.Function;

import io.vsrp.domain.Report;

public class FileParser {

    private String filename;
    
    public FileParser(String filename) {
        this.filename = filename;
    }
    
    public String getFilename() {
        return filename;
    }
    
    public Report parse() throws IOException {
        Files.lines(Paths.get(this.filename))
            .map(l -> l.trim())
            .filter(l -> l.contains("applog.msg"))
//            .map(processLine);
//            .map(expandFunction)
            .forEach(l -> System.out.println(l));
        
        return null;
    }
    
    static Consumer<String> processLine = l -> System.out.println(l+"@@");

    static Function<String, String> expandFunction = (l) -> {
      return l+"..";  
    };
    
    public static void main(String ... args) throws IOException {
        String filename = "testfile.log";
        FileParser fp = new FileParser(filename);
        Report report = fp.parse();
//        System.out.println(fp.expandFunction.apply("hell"));
    }
}
