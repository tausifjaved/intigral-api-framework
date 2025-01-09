package tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.intigral.assignment.models.Booking;
import org.intigral.assignment.models.BookingDates;
import org.intigral.assignment.utils.ConfigManager;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * Test class for validating API operations related to booking functionality.
 * Implements test cases for various scenarios such as valid, invalid, and special input handling.
 */
@Listeners(org.intigral.assignment.listeners.ExtentReportListener.class)
public class BookingTest extends BaseTest {

    /**
     * Stores the booking ID created during tests for validation purposes.
     */
    private int bookingId;

    /**
     * Test to create a booking with valid data and verify its creation.
     */
    @Test(priority = 1)
    public void addBooking() {
        BookingDates bookingDates = new BookingDates("2022-01-01", "2024-01-01");
        Booking booking = createBooking("testFirstName", "lastName", 10.11, true, bookingDates, "testAdd");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .log().all()
                .when()
                .post(ConfigManager.get("addBookingEndpoint"))
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200, "Booking creation failed!");

        bookingId = response.jsonPath().getInt("bookingid");
        Assert.assertTrue(bookingId > 0, "Invalid booking ID!");
    }

    /**
     * Test to validate the details of a booking using its booking ID.
     * Depends on {@link #addBooking()}.
     */
    @Test(priority = 2, dependsOnMethods = {"addBooking"})
    public void validateBooking() {
        Response response = given()
                .when()
                .get(ConfigManager.get("addBookingEndpoint") + "/" + bookingId)
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200, "Booking retrieval failed!");

        String firstname = response.jsonPath().getString("firstname");
        Assert.assertEquals(firstname, "testFirstName", "Firstname does not match!");
    }

    /**
     * Test to add a booking with invalid (empty) data and verify the failure response.
     */
    @Test(priority = 3)
    public void addBookingWithInvalidData() {
        Booking booking = new Booking(); // Empty object to simulate missing data

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .log().all()
                .when()
                .post(ConfigManager.get("addBookingEndpoint"))
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 400, "Invalid booking should return 400!");
    }

    /**
     * Test to add a booking with invalid date range and verify the failure response.
     */
    @Test(priority = 4)
    public void addBookingWithInvalidDates() {
        BookingDates bookingDates = new BookingDates("2024-01-01", "2022-01-01"); // Invalid date range

        Assert.assertFalse(bookingDates.isDateRangeValid(), "Check-in date must be before or equal to the check-out date!");

        Booking booking = createBooking("testFirstName", "lastName", 10.11, true, bookingDates, "testAdd");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .log().all()
                .when()
                .post(ConfigManager.get("addBookingEndpoint"))
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 400, "Invalid date range should return 400!");
    }

    /**
     * Test to add a booking with empty booking dates and verify the failure response.
     */
    @Test(priority = 5)
    public void addBookingWithEmptyBookingDates() {
        Booking booking = createBooking("testFirstName", "lastName", 10.11, true, null, "testAdd");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .log().all()
                .when()
                .post(ConfigManager.get("addBookingEndpoint"))
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 400, "Missing booking dates should return 400!");
    }

    /**
     * Test to validate the response schema of a booking.
     */
    @Test(priority = 6)
    public void validateBookingResponseSchema() {
        Response response = given()
                .when()
                .get(ConfigManager.get("addBookingEndpoint") + "/" + bookingId)
                .then()
                .log().all()
                .extract().response();

        try {
            response.then().assertThat().body(matchesJsonSchemaInClasspath("booking_schema.json"));
        } catch (Exception e) {
            Assert.fail("Schema validation failed: " + e.getMessage());
        }
    }

    /**
     * Test to create a booking with special characters in the firstname and lastname fields.
     */
    @Test(priority = 7)
    public void addBookingWithSpecialCharacters() {
        BookingDates bookingDates = new BookingDates("2023-01-01", "2023-01-10");
        Booking booking = createBooking("!@#$%^&*", "()_+", 10.11, true, bookingDates, "testAdd");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .log().all()
                .when()
                .post(ConfigManager.get("addBookingEndpoint"))
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 200, "Special character handling failed!");
    }

    /**
     * Data provider for invalid booking input test cases.
     *
     * @return An array of invalid booking test data.
     */
    @DataProvider(name = "invalidBookingData")
    public Object[][] invalidBookingData() {
        return new Object[][]{
                {"", "lastName", 10.11, true}, // Missing firstname
                {"testFirstName", "", 10.11, true}, // Missing lastname
                {"testFirstName", "lastName", -10, true}, // Negative totalprice
                {"testFirstName", "lastName", 10.11, false} // Missing dates
        };
    }

    /**
     * Test to add bookings with invalid inputs and verify the failure responses.
     *
     * @param firstname   Firstname of the booking.
     * @param lastname    Lastname of the booking.
     * @param totalprice  Total price for the booking.
     * @param depositpaid Deposit status for the booking.
     */
    @Test(dataProvider = "invalidBookingData", priority = 8)
    public void addBookingWithInvalidInputs(String firstname, String lastname, double totalprice, boolean depositpaid) {
        Booking booking = createBooking(firstname, lastname, totalprice, depositpaid, null, "testAdd");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(booking)
                .log().all()
                .when()
                .post(ConfigManager.get("addBookingEndpoint"))
                .then()
                .log().all()
                .extract().response();

        Assert.assertEquals(response.statusCode(), 400, "Invalid booking should return 400!");
    }

    /**
     * Utility method for creating a booking object.
     *
     * @param firstname       Firstname of the booking.
     * @param lastname        Lastname of the booking.
     * @param totalprice      Total price for the booking.
     * @param depositpaid     Deposit status for the booking.
     * @param bookingDates    Booking dates for the booking.
     * @param additionalneeds Additional needs for the booking.
     * @return A booking object.
     */
    private Booking createBooking(String firstname, String lastname, double totalprice, boolean depositpaid, BookingDates bookingDates, String additionalneeds) {
        Booking booking = new Booking();
        booking.setFirstname(firstname);
        booking.setLastname(lastname);
        booking.setTotalprice(totalprice);
        booking.setDepositpaid(depositpaid);
        booking.setBookingdates(bookingDates);
        booking.setAdditionalneeds(additionalneeds);
        return booking;
    }
}
