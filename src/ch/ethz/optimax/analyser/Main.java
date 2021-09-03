package ch.ethz.optimax.analyser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting...");
        HashMap<String, HashMap<String, HashMap<String, Integer>>> dateCountHashMap
                = new HashMap <String, HashMap<String, HashMap<String, Integer>>> ();

        Collection<File> files = FileUtils.listFiles(
                new File(Config.rootDir),
                new RegexFileFilter("^.*-om_.*\\.zip$"),
                DirectoryFileFilter.DIRECTORY
        );

        System.out.println("Files are filtered...");

        for (Object file : files) {
            String fullFileName = file.toString();
            String[] tokens = fullFileName.split("/");
            String fileName = tokens[tokens.length - 1];
            System.out.println(fileName);
        }

    }
}
