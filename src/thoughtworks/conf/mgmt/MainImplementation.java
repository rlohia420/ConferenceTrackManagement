package thoughtworks.conf.mgmt;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class MainImplementation {
    public static void main(String[] args) throws IOException {
        int numberOfTracks = 2;
        //Input file will be expecting from source else sampleInput.txt is the default sample problem
        //Default value of track is 2 if any invalid input is provided
        String fileName = args.length == 1 ? args[0] : "SampleInput.txt";
        System.out.println("***** Start the process ****** ");
        System.out.println("Input file processed is " + fileName);
        System.out.println("Enter the number of tracks in Conference ");
        Scanner sc = new Scanner(System.in);
        try {
            numberOfTracks = sc.nextInt();
        } catch (Exception ex) {
            System.out.println("Invalid tracks are inputted");
        }
        final List<String> lines = readFile(fileName);
        Conference conference = new Conference(Conference.CONFERENCE_NAME,numberOfTracks);
        conference.start(lines, numberOfTracks);
        sc.close();
    }

    private static List<String> readFile(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName));
    }

}
