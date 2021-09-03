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
        HashMap dateCountHashMap = new HashMap <String, HashMap<String, HashMap<String, Integer>>> ();

        Collection files = FileUtils.listFiles(
                new File(Config.rootDir),
                new RegexFileFilter("^.*\\.xml$"),
                DirectoryFileFilter.DIRECTORY
        );

        Iterator iterator = files.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
