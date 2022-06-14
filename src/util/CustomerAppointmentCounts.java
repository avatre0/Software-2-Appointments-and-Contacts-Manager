package util;

public class CustomerAppointmentCounts {

    private int customerID;
    private int count;

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

    public CustomerAppointmentCounts(int customerID, int count) {
        this.customerID = customerID;
        this.count = count;
    }
}
