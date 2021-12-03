public abstract class Recording implements Playable {
    protected String artistName = "";
    protected String songName = "";
    protected int DURATION_IN_SECONDS = 0;
    protected double playSpeed = 0.0;
    protected char type;

    // Recordigs default constructor
    Recording() {
        this.artistName = "";
        this.songName = "";
        this.DURATION_IN_SECONDS = 0;
        this.playSpeed = 0;
    }

    // Constructor to make basic recording
    protected Recording(String aName, String sName, int duration) {
        this.artistName = aName;
        this.songName = sName;
        this.DURATION_IN_SECONDS = duration;
        this.type = '\0';
    }

    protected String getArtist() {
        return this.artistName;
    }

    protected String getSongName() {
        return this.songName;
    }

    protected char getType(){
        return type;
    }

    protected int getDuration() {
        return this.DURATION_IN_SECONDS;
    }

    protected double getPlaySpeed() {
        return this.playSpeed;
    }

    // Method to play playlist with a throws to catch recordings with zero duration
    public void play() throws Unplayable {
        if (DURATION_IN_SECONDS != 0) {
            System.out.println("Now Playing: " + artistName + " - " + songName + "[" + DURATION_IN_SECONDS / 60 + "m"
                    + DURATION_IN_SECONDS % 60 + "s]");
        } else {
            throw new Unplayable("Sorry, the song you are trying to play has no duration...");
        }
    }

    // return info of recording
    public String toString() {
        return artistName + " - " + songName + " [" + DURATION_IN_SECONDS / 60 + "m" + DURATION_IN_SECONDS % 60 + "s]";
    }
}
