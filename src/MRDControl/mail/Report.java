package MRDControl.mail;

/**
 * Created on 11/2/23.
 */
public enum Report {
    SEVEN_AM("7:00 AM"),
    TEN_AM("10:00 AM");

    final String hourString;

    Report(String hourString) {
        this.hourString = hourString;
    }

    public String getHourString() {
        return hourString;
    }
}
