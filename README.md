# Sploteam UI Test Automation

Java · Selenium WebDriver · JUnit 4 · Maven · Page Object Model

A course-based UI automation project for the Sploteam sports platform. I wrote the test code independently while completing assignments against a training website. This repository presents that learning work, including positive and negative user flows, browser interaction and an initial Page Object Model structure.

## What the project demonstrates

- Browser automation with Chrome and Selenium WebDriver.
- JUnit setup, teardown and assertions.
- Page classes that group locators and element access methods.
- Explicit waits, element lists, loops and conditional checks.
- Local configuration for environment access and application credentials.

## Scenarios in the source

The suite contains **33 test methods**. This is a count of implemented exercises, not a verified passing result.

| Area | Examples |
| --- | --- |
| Main page | Header, sports and camp sections, request form, link counts |
| Authentication | Valid login, invalid credentials, missing or malformed email |
| Profile | Profile details, name editing, gender selection |
| Events | Calendar navigation; arena, sport, player level and city filters |
| Camps | Monthly listings and camp filtering |
| Forms | Registration validation, password reset request, balance input validation |

Start with [SploteamTest.java](src/test/java/com/juneit/SploteamTest.java) for the scenarios and [pages/](src/test/java/com/juneit/pages) for the page classes.

## Project structure

```text
src/test/
├── java/com/juneit/
│   ├── SploteamTest.java          # Original UI exercises
│   ├── PropertiesLoader.java      # Local configuration loading
│   └── pages/                     # Eight page classes and their locators
└── resources/
    └── config-template.properties # Configuration example without credentials
pom.xml                            # Java and dependency configuration
```

## Run locally

Prerequisites: **JDK 21**, **Maven 3.9+**, Google Chrome, and authorized access to the original training environment with a dedicated test account. Internet access may be required for Maven dependencies and Selenium driver management.

1. Clone the repository and enter its directory:

   ```sh
   git clone https://github.com/DmSitnikov/sploteam_automation_basic.git
   cd sploteam_automation_basic
   ```

2. Copy `src/test/resources/config-template.properties` to `src/test/resources/config.properties`. Fill in every value. HTTP Basic authentication and the application's test account use separate settings. The local file is ignored by Git. Use Java properties escaping for backslashes and `\uXXXX` escapes for non-ASCII characters in values.

3. Check that Maven uses JDK 21, then compile without contacting the website:

   ```sh
   mvn -version
   mvn -DskipTests test
   ```

4. Start with one page-level scenario:

   ```sh
   mvn "-Dtest=SploteamTest#assertMainPageIsLoaded" test
   ```

   The suite loads all configuration values even for this single scenario. Chrome opens visibly. Test reports are written to `target/surefire-reports/`.

The full suite is available with `mvn test`. Review its side effects first: tests edit account details, one switches gender without restoring it, and the password-reset scenario sends a reset email. Use an isolated training account; restore its expected state between runs. The name-editing scenario expects `test-display-name` and temporarily changes it to a fixture name.

## Current status and limitations

This is a learning project with an initial Page Object Model implementation. It has not been validated against the current training website during this presentation cleanup. Access to that environment and a valid account are required for end-to-end verification.

- Some exercises use 2024 camp data, fixed calendar positions and environment-specific filter options.
- Several `isDisplayed()` calls do not assert the returned result; some exercises print observations rather than assert outcomes.
- Locators include absolute XPath and generated CSS class names, which may need updating.
- A browser validation message expects German text; other expected UI messages are Russian.
- The city-switch exercise contains an early `break` that prevents its intended filtering assertions from running when dates exist.
- Some loops can pass without checking any matching events. Profile state is not always restored after a failure.

Next improvements would be stronger assertions, reliable test data, more stable locators and guaranteed account cleanup. The existing exercises are retained so the repository reflects the author's actual work.
