# Booking API Automation Testing

## Overview
This project is designed to automate testing for a Booking API. It includes functional test cases to validate various endpoints and scenarios such as adding a booking, validating booking data, handling invalid data, and testing response schemas.

The project uses:
- **Java** as the programming language
- **RestAssured** for API testing
- **TestNG** as the testing framework
- **ExtentReports** for generating detailed HTML reports
- **Maven** for dependency management

---

## Project Structure
```
BookingAPITests
├── src
│   ├── main
│   │   ├── java
│   │   │   ├── org
│   │   │   │   ├── intigral
│   │   │   │   │   ├── assignment
│   │   │   │   │   │   ├── listeners
│   │   │   │   │   │   │   └── ExtentReportListener.java
│   │   │   │   │   │   ├── models
│   │   │   │   │   │   │   ├── Booking.java
│   │   │   │   │   │   │   └── BookingDates.java
│   │   │   │   │   │   ├── utils
│   │   │   │   │   │   │   └── ConfigManager.java
│   ├── test
│       ├── java
│       │   ├── tests
│       │   │   ├── BaseTest.java
│       │   │   ├── BookingTest.java
│       │   │   └── SampleTest.java
│       └── resources
│           └── config.properties
├── target
│   ├── ExtentReport.html
├── pom.xml
└── README.md
```

---

## Prerequisites
- **Java** (JDK 8 or higher)
- **Maven** (for dependency management)
- IDE (e.g., IntelliJ IDEA, Eclipse)
- Internet connection to fetch Maven dependencies

---

## Setup
1. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
2. Navigate to the project directory:
   ```bash
   cd BookingAPITests
   ```
3. Install dependencies using Maven:
   ```bash
   mvn clean install
   ```

---

## Configuration
Update the `config.properties` file located in `src/main/resources` with the correct base URL and endpoint details:
```properties
baseUrl=https://restful-booker.herokuapp.com
addBookingEndpoint=/booking
```

---

## Running Tests

### Command Line
1. Run the tests using Maven:
   ```bash
   mvn test
   ```

### IDE
1. Import the project into your IDE.
2. Right-click on the `test` folder or specific test classes and select `Run`.

---

## Reporting
After running the tests, an **Extent Report** will be generated at:
```
target/ExtentReport.html
```
Open this file in your browser to view detailed test execution results.

---

## Key Components

### Listeners
- **`ExtentReportListener`**: Captures test execution events and logs them into the Extent Report.

### Models
- **`Booking`**: Represents the payload structure for creating a booking.
- **`BookingDates`**: Represents the date range for bookings.

### Utils
- **`ConfigManager`**: Loads configuration from `config.properties`.

### Tests
- **`BaseTest`**: Sets up the test environment and reporting.
- **`BookingTest`**: Contains the primary test cases for validating Booking API functionality.

---

## Test Scenarios
### Positive Scenarios:
1. Add a booking with valid data.
2. Retrieve and validate the created booking.
3. Validate the response schema of a booking.

### Negative Scenarios:
1. Add a booking with missing data.
2. Add a booking with invalid date ranges.
3. Add a booking with special characters in fields.

---

## Extending the Framework
To add a new test:
1. Create a new class in the `tests` package.
2. Extend the `BaseTest` class.
3. Annotate test methods with `@Test` and add your logic.
4. Run the tests and view the results in the report.

---

## License
This project is licensed under the MIT License. See the LICENSE file for details.
