package util;

/**
 * Helper Class to hold Appointment Types with an count of the occurrences
 */
public class TypeAppointment {

    private String typeName;
    private int count;

    /**
     * Constructor
     * @param typeName
     * @param count
     */
    public TypeAppointment(String typeName, int count) {
        this.typeName = typeName;
        this.count = count;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
