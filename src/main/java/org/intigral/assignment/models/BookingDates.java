package org.intigral.assignment.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents booking dates, including check-in and check-out dates,
 * with validation logic for date formats and date ranges.
 */
public class BookingDates {

    /**
     * The check-in date for the booking in the format yyyy-MM-dd.
     */
    private String checkin;

    /**
     * The check-out date for the booking in the format yyyy-MM-dd.
     */
    private String checkout;

    /**
     * Default constructor for creating an empty BookingDates instance.
     */
    public BookingDates() {
    }

    /**
     * Parameterized constructor to initialize check-in and check-out dates.
     *
     * @param checkin  The check-in date in the format yyyy-MM-dd.
     * @param checkout The check-out date in the format yyyy-MM-dd.
     */
    public BookingDates(String checkin, String checkout) {
        this.checkin = checkin;
        this.checkout = checkout;
    }

    /**
     * Gets the check-in date.
     *
     * @return the check-in date as a string in the format yyyy-MM-dd.
     */
    public String getCheckin() {
        return checkin;
    }

    /**
     * Sets the check-in date.
     *
     * @param checkin the check-in date as a string in the format yyyy-MM-dd.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    public void setCheckin(String checkin) {
        if (isValidDate(checkin)) {
            this.checkin = checkin;
        } else {
            throw new IllegalArgumentException("Invalid check-in date format: " + checkin);
        }
    }

    /**
     * Gets the check-out date.
     *
     * @return the check-out date as a string in the format yyyy-MM-dd.
     */
    public String getCheckout() {
        return checkout;
    }

    /**
     * Sets the check-out date.
     *
     * @param checkout the check-out date as a string in the format yyyy-MM-dd.
     * @throws IllegalArgumentException if the date format is invalid.
     */
    public void setCheckout(String checkout) {
        if (isValidDate(checkout)) {
            this.checkout = checkout;
        } else {
            throw new IllegalArgumentException("Invalid check-out date format: " + checkout);
        }
    }

    /**
     * Validates if the input string is in a valid date format (yyyy-MM-dd).
     *
     * @param date The date string to validate.
     * @return true if the date is valid, false otherwise.
     */
    private boolean isValidDate(String date) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks if the check-in date is before or equal to the check-out date.
     *
     * @return true if the check-in date is valid relative to the check-out date, false otherwise.
     */
    public boolean isDateRangeValid() {
        if (checkin == null || checkout == null) {
            return false;
        }
        LocalDate checkinDate = LocalDate.parse(checkin);
        LocalDate checkoutDate = LocalDate.parse(checkout);
        return !checkinDate.isAfter(checkoutDate);
    }

    /**
     * Provides a string representation of the BookingDates instance.
     *
     * @return a string in the format "BookingDates{checkin='checkin', checkout='checkout'}".
     */
    @Override
    public String toString() {
        return "BookingDates{" +
                "checkin='" + checkin + '\'' +
                ", checkout='" + checkout + '\'' +
                '}';
    }
}
