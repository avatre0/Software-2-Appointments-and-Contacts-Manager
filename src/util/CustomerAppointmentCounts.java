package util;

/**
 * Helper Object to hold a count of appointments with a customer ID
 */
public class CustomerAppointmentCounts {

    private int customerID;
    private int count;
    private String name;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Constructor
     * @param customerID
     * @param count
     * @param name
     */
    public CustomerAppointmentCounts(int customerID, int count, String name) {
        this.customerID = customerID;
        this.count = count;
        this.name = name;
    }
}
