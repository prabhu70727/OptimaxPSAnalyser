package ch.ethz.optimax.analyser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

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
            tokens = fileName.split("_");
            String participantID = tokens[0].split("-")[0];
            long timestamp = Long.parseLong(tokens[tokens.length-1].split("\\.")[0]);
            Instant instant = Instant.ofEpochSecond(timestamp * 1000);
            Date date = Date.from(instant);
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            String dateString = simpleDateFormat.format(date);
            System.out.println(participantID + " " + dateString);
        }

    }
}
