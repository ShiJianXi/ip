public class Activity {
    private String name;
    private static int noOfActivity;

    public Activity(String name) {
        this.name = name;
        noOfActivity++;
    }

    public int getNoOfActivity() {
        return noOfActivity;
    }

    @Override
    public String toString() {
        return name;
    }
}
