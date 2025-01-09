package org.intigral.assignment.models;

/**
 * Represents a booking entity with details about the customer, booking dates,
 * total price, deposit status, and additional needs.
 */
public class Booking {

    /**
     * The first name of the customer making the booking.
     */
    private String firstname;

    /**
     * The last name of the customer making the booking.
     */
    private String lastname;

    /**
     * The total price of the booking.
     */
    private double totalprice;

    /**
     * Indicates whether the deposit for the booking has been paid.
     */
    private boolean depositpaid;

    /**
     * The booking dates including check-in and check-out details.
     */
    private BookingDates bookingdates;

    /**
     * Additional needs or requests specified by the customer.
     */
    private String additionalneeds;

    /**
     * Gets the additional needs of the customer.
     *
     * @return the additional needs of the customer.
     */
    public String getAdditionalneeds() {
        return additionalneeds;
    }

    /**
     * Sets the additional needs of the customer.
     *
     * @param additionalneeds the additional needs of the customer.
     */
    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }

    /**
     * Gets the booking dates.
     *
     * @return the booking dates including check-in and check-out details.
     */
    public BookingDates getBookingdates() {
        return bookingdates;
    }

    /**
     * Sets the booking dates.
     *
     * @param bookingdates the booking dates including check-in and check-out details.
     */
    public void setBookingdates(BookingDates bookingdates) {
        this.bookingdates = bookingdates;
    }

    /**
     * Checks if the deposit for the booking has been paid.
     *
     * @return {@code true} if the deposit has been paid; {@code false} otherwise.
     */
    public boolean isDepositpaid() {
        return depositpaid;
    }

    /**
     * Sets the deposit paid status for the booking.
     *
     * @param depositpaid {@code true} if the deposit has been paid; {@code false} otherwise.
     */
    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    /**
     * Gets the total price of the booking.
     *
     * @return the total price of the booking.
     */
    public double getTotalprice() {
        return totalprice;
    }

    /**
     * Sets the total price of the booking.
     *
     * @param totalprice the total price of the booking.
     */
    public void setTotalprice(double totalprice) {
        this.totalprice = totalprice;
    }

    /**
     * Gets the last name of the customer.
     *
     * @return the last name of the customer.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the last name of the customer.
     *
     * @param lastname the last name of the customer.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Gets the first name of the customer.
     *
     * @return the first name of the customer.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the first name of the customer.
     *
     * @param firstname the first name of the customer.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
}
