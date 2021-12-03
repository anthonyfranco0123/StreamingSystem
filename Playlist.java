
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playlist implements Playable {

    // Class variables to set up arraylists and variables to use in methods
    private static ArrayList<Recording> numberOfTimesPlayedList = new ArrayList<Recording>();
    static ArrayList<String> playlists = new ArrayList<String>();
    static List<List<Recording>> recordings = new ArrayList<List<Recording>>();
    static List<List<Integer>> recordingsStats = new ArrayList<List<Integer>>();

    // Object to make call to unplayble class if duration is zero
    /* NOTE only call not implemented*/
    Unplayable durationException = new Unplayable("We cannot play this song... it has a duraction of 0 seconds");

    // Adds playlist to the arraylis playlists
    public void addPlaylist(String p) {
        playlists.add(p);
    }

    // Finds the index of a playlist by using playlist name
    public int getPlaylistIndex(String p) {
        for (int i = 0; i < playlists.size(); i++) {
            if (playlists.get(i).equals(p)) {
                return i;
            }
        }
        return -1;
    }

    // Method to add audiorecording object to recordings List
    public void addA(String playlistName, String artistName, String songName, int dur, double bit) {
        for (int i = 0; i < playlists.size(); i++) {
            if (playlistName.equals(playlists.get(i))) {
                recordings.get(i).add(new AudioRecording(artistName, songName, dur, bit));
                System.out.println("THis is addA: "+ recordings.get(i));
                setRecordingStats(i);
            }
        }
    }

    // Method to add videorecording object to recordings List
    public void addV(String playlistName, String artistName, String songName, int dur, double frame) {
        for (int i = 0; i < playlists.size(); i++) {
            if (playlistName.equals(playlists.get(i))) {
                recordings.get(i).add(new VideoRecording(artistName, songName, dur, frame));
                System.out.println("This is addV: "+recordings.get(i));
                setRecordingStats(i);
            }
        }
    }

    //Method to fill List with more rows as new users are added
    //Also adds rows to make space for stats List
    public void addNewRecording(){
        ArrayList<Recording> row = new ArrayList<Recording>();
        recordings.add(row);
        
        ArrayList<Integer> rowToo = new ArrayList<Integer>();
        recordingsStats.add(rowToo);
    }

    // Adds playlist from user class to the arraylist playlists
    public void addNewPlaylist(String playlist) {
        playlists.add(playlist);
    }

    // Method that adds all songs from a playlist to another users playlist
    public void add(int playlistLoc, int playlistLoc2) {
        for(int i = 0; i < recordings.get(playlistLoc).size(); i++){
            if('A'==(recordings.get(playlistLoc).get(i).getType())){
                AudioRecording ar = (AudioRecording) recordings.get(playlistLoc).get(i);
                addA(playlists.get(playlistLoc2), ar.getArtist(), ar.getSongName(), ar.getDuration(), ar.getBitrate());
            } else if('V'==(recordings.get(playlistLoc).get(i).getType())){
                VideoRecording vr = (VideoRecording) recordings.get(playlistLoc).get(i);
                addV(playlists.get(playlistLoc2), vr.getArtist(), vr.getSongName(), vr.getDuration(), vr.getFrameRate());
            }
        }
    }

    @Override
    public void play() {
        // Not needed but kept due to old objectives
    }

    // Sets each spot in a row to zero in recordings stats arraylist upon recording creation
    public void setRecordingStats(int playlistIndex){
        recordingsStats.get(playlistIndex).add(0);
    }

    //Increments stat by one for each recording played
    public void incrementRecordingStats(int playlistIndex, int recordingSpot){
        int temp = recordingsStats.get(playlistIndex).get(recordingSpot);
        temp+=1;
        recordingsStats.get(playlistIndex).set(recordingSpot, temp);
    }
}