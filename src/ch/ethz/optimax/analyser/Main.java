package ch.ethz.optimax.analyser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Starting...");
        List<String> participants = new ArrayList<>();
        List<String> sensors = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        HashMap<String, Integer> participantMap = new HashMap<>();
        HashMap<String, Integer> sensorMap = new HashMap<>();
        HashMap<String, Integer> dateMap = new HashMap<>();

        Collection<File> files = FileUtils.listFiles(
                new File(Config.rootDir),
                new RegexFileFilter("^.*-om_.*\\.zip$"),
                DirectoryFileFilter.DIRECTORY
        );

        System.out.println("Files are filtered...");
        int countFiles = 0;

        System.out.println("First run...");
        for (Object file : files) {
            countFiles++;
            String fullFileName = file.toString();
            String[] tokens = fullFileName.split("/");
            String fileName = tokens[tokens.length - 1];
            tokens = fileName.split("_");

            // getting the participant id
            String participantID = tokens[0].split("-")[0];
            long timestamp = Long.parseLong(tokens[tokens.length-1].split("\\.")[0]);

            // getting the sensor
            String sensor = tokens[1];

            // getting the date
            Instant instant = Instant.ofEpochSecond(timestamp/1000);
            Date date = Date.from(instant);
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            String dateString = simpleDateFormat.format(date);

            if(!participants.contains(participantID)) participants.add(participantID);
            if(!sensors.contains(sensor)) sensors.add(sensor);
            if(!dates.contains(dateString)) dates.add(dateString);
        }
        System.out.println("The number of files processed: " + countFiles);
        System.out.println("The number of participants: " + participants.size());
        System.out.println("The number of sensors: " + sensors.size());
        System.out.println("The number of dates: " + dates.size());

        // Sorting the dates array
        System.out.println("Sorting the participants array");
        participants.sort(Comparator.naturalOrder());

        System.out.println("Sorting the dates array");
        dates.sort(Comparator.naturalOrder());

        // Loading the lists into hash tables
        System.out.println("Loading the lists into hash tables");
        int participantListCount = 0;
        for (String participant: participants) {
            participantMap.put(participant, participantListCount);
            participantListCount++;
        }

        int sensorListCount = 0;
        for (String sensor: sensors) {
            sensorMap.put(sensor, sensorListCount);
            sensorListCount++;
        }

        int dateListCount = 0;
        for (String date: dates) {
            dateMap.put(date, dateListCount);
            dateListCount++;
        }

        long[][][] participantsSensorsDatesCount
                = new long[participantListCount][sensorListCount][dateListCount];

        System.out.println("Initialize the 3D array...");
        for (int i=0; i < participantListCount; i++) {
            for (int j=0; j < sensorListCount; j++) {
                for (int k=0; k < dateListCount; k++) {
                    participantsSensorsDatesCount[i][j][k] = 0;
                }
            }
        }

        System.out.println("Second run...");
        countFiles = 0;
        for (Object file : files) {
            countFiles++;
            String fullFileName = file.toString();
            String[] tokens = fullFileName.split("/");
            String fileName = tokens[tokens.length - 1];
            tokens = fileName.split("_");

            // getting the participant id
            String participantID = tokens[0].split("-")[0];
            long timestamp = Long.parseLong(tokens[tokens.length-1].split("\\.")[0]);

            // getting the sensor
            String sensor = tokens[1];

            // getting the date
            Instant instant = Instant.ofEpochSecond(timestamp/1000);
            Date date = Date.from(instant);
            SimpleDateFormat simpleDateFormat
                    = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
            String dateString = simpleDateFormat.format(date);

            int participantIndex = participantMap.get(participantID);
            int sensorIndex = sensorMap.get(sensor);
            int dateIndex = dateMap.get(dateString);

            if ((participantIndex <0 || participantIndex >= participantListCount)
                || (sensorIndex <0 || sensorIndex >= sensorListCount)
                || (dateIndex <0 || dateIndex >= dateListCount)){
                System.out.println("Error in index...");
                return;
            }
            participantsSensorsDatesCount[participantIndex][sensorIndex][dateIndex]++;
        }
        System.out.println("The number of files processed is: " + countFiles);

        System.out.println("Printing the 3D array");
        for (int i=0; i < participantListCount; i++) {
            for (int j=0; j < sensorListCount; j++) {
                for (int k=0; k < dateListCount; k++) {
                    long value = participantsSensorsDatesCount[i][j][k];
                    if (value == 0) continue;
                    System.out.println("Participant: " + participants.get(i) +
                            " Sensor: " + sensors.get(j) +
                            " Date: " + dates.get(k) +
                            " Count: " + value);
                }
            }
        }
    }
}
