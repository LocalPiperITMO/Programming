package BasicsForHumans;

public class DateTracker {
    public static class Day {
        private static int day_count = 1;

        private static int getDay_count() {
            return day_count;
        }
    }

    public void startNewDay() {
        System.out.println("Day " + Day.getDay_count() + " came to an end");
        ++Day.day_count;
        System.out.println("Day " + Day.getDay_count() + " started");
    }
}
