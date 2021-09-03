package ch.ethz.optimax.analyser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting...");
        HashMap dateCountHashMap = new HashMap <String, HashMap<String, HashMap<String, Integer>>> ();

        Collection files = FileUtils.listFiles(
                new File(Config.rootDir),
                new RegexFileFilter("^.*-om_.*\\.zip$"),
                DirectoryFileFilter.DIRECTORY
        );

        System.out.println("Files are filtered...");

        Iterator iterator = files.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
