package thoughtworks.conf.mgmt;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Conference {
    public static final String CONFERENCE_NAME = "ThoughWorks Conference";
    private Integer noOfTracks;
    private final String name;
    private List<Track> tracks;

    public Conference(String name, int noOfTracks) {
        this.noOfTracks = Integer.valueOf(noOfTracks);
        this.name = name;
        this.tracks = new ArrayList(noOfTracks);
    }

    public Integer noOfTracks() {
        return this.noOfTracks;
    }

    void addTrack(String name) {
        this.tracks.add(new Track(name));
    }

    private void scheduleTalks(List<Talk> unscheduledTalks) {
        System.out.println("Conference Name : " + name);
        for (Track track : tracks) {
            track.scheduleTalks(unscheduledTalks);
            track.scheduleEmptyTalks();
            System.out.println(track.toString());
        }
    }

    public void start(List<String> lines, int numberOfTracks) {
        final List<Talk> unscheduledTalks = MapStringsIntoTalks(lines.stream().sorted());
        for (int i = 1; i <= noOfTracks; i++) {
            addTrack("Track " + i + " : ");
        }
        scheduleTalks(unscheduledTalks);
    }

    /**
     * @param lines
     * @return
     */
    static List<Talk> MapStringsIntoTalks(Stream<String> lines) {
        return lines.map(line -> {
            String trimmedLine = line.trim();
            int lastSpaceIndex = line.lastIndexOf(" ");
            if (lastSpaceIndex == -1)
                throw new InvalidTalkException("Invalid talk name in file " + line);

            String talkName = trimmedLine.substring(0, lastSpaceIndex);
            String duration = trimmedLine.substring(lastSpaceIndex + 1);
            if (duration.endsWith("min")) {
                String minutes = duration.replaceFirst("min", "");
                return Talk.normalTalk(talkName, Duration.ofMinutes(Long.valueOf(minutes)));
            } else if (duration.endsWith("lightning")) {
                return Talk.shortTalk(talkName);
            } else {
                throw new InvalidTalkException("Invalid talk name in file " + line);
            }
        }).collect(Collectors.toList());
    }

    private static class InvalidTalkException extends RuntimeException {
        public InvalidTalkException(String description) {
            super(description);
        }
    }
}
