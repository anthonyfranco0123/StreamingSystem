public class VideoRecording extends Recording {
    private double frameRate;

    // Constructor to create videorecording object
    public VideoRecording(String aName, String sName, int duration, double fRate) {
        super(aName, sName, duration);
        this.frameRate = fRate;
        super.type = 'V';
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

    protected double getFrameRate(){
        return this.frameRate;
    }

    // Return info of videorecording
    @Override
    public String toString() {
        return super.toString() + " framerate: " + this.frameRate;
    }
}
