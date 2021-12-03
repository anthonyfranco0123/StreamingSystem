public class User implements Playable {

    // Creats the class variables
    private String name;
    private String id;
    private String playlist;

    // Constructor to set class variables equal to whatever is passed in the parameters
    // Creates playlist class object and creates playlist value for an arraylist
    // Also creates recording arraylist for specified playlist
    public User(String n, String id, String playlist) {
        this.name = n;
        this.id = id;
        this.playlist = playlist;
        Playlist p = new Playlist();
        p.addNewPlaylist(playlist);
        p.addNewRecording();
        System.out.println("\nSuccess! You have been added to the system. Here is your unique id: " + this.id + "\n");
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String getPlaylist() {
        return this.playlist;
    }

    @Override
    public void play() {
        // Kept due to old objectives
    }
}