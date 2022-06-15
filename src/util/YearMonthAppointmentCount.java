package util;

/**
 * Helper Object to store Year Month Appointments and their counts
 */
public class YearMonthAppointmentCount {
    private int year;
    private String month;
    private int count;

    /**
     * Constructor
     * @param year
     * @param month
     * @param count
     */
    public YearMonthAppointmentCount(int year, String month, int count) {
        this.year = year;
        this.month = month;
        this.count = count;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
