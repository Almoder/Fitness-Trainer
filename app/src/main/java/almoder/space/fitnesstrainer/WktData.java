package almoder.space.fitnesstrainer;

public class WktData {

    private String title;
    private int[] exes = null;

    public WktData(String title) {
        this.title = title;
    }

    public WktData(String title, int[] exercises) {
        this.title = title;
        this.exes = exercises;
    }

    public String title() {
        return title;
    }
    public String count() { return String.valueOf(exes == null ? 0 : exes.length); }
    public int[] exercises() { return exes; }
}
