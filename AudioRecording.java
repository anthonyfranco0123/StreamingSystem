public class AudioRecording extends Recording {
    private double bitrate;

    // Constructor to form object of audiorecording
    public AudioRecording(String aName, String sName, int duration, double bRate) {
        super(aName, sName, duration);
        this.bitrate = bRate;
        super.type = 'A';
    }

    @Override
    public char getType() {
        return type;
    }

    @Override
    protected String getSongName() {
        return this.songName;
    }

    @Override
    protected int getDuration(){
        return this.DURATION_IN_SECONDS;
    }

    protected double getBitrate(){
        return this.bitrate;
    }

    // return info of audio info
    @Override
    public String toString() {
        return super.toString() + " bitrate: " + this.bitrate;
    }

}