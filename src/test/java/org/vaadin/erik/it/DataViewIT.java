package org.vaadin.erik.it;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataViewIT {

    private static final String[] MENU_ITEMS = new String[]{ "Repository", "JPA", "Jooq DTO", "Jooq Record", "JDBC" };

    private static final String FIRST_NAME = "Bob";
    private static final String LAST_NAME = "McBobson";
    private static final String EMAIL = "bob.mcbobson@example.com";
    private static final String PHONE = "020202";
    private static final LocalDate DATE_OF_BIRTH = LocalDate.of(1960, 1, 1);
    private static final String OCCUPATION = "CEO of McBobson & C:O";
    private static final boolean IMPORTANT = true;

    private static Browser browser;

    private Page page;

    @BeforeAll
    static void setUpClass() {
        Playwright playwright = Playwright.create();
        BrowserType browserType = playwright.chromium();
        browser = browserType.launch(new BrowserType.LaunchOptions().withHeadless(false));
    }

    @BeforeEach
    void setUp() {
        BrowserContext context = browser.newContext(new Browser.NewContextOptions().withViewport(880, 640));
        page = context.newPage();
        page.navigate("http://localhost:8080");
    }

    @Test
    void testALittleBitOfEverything() {
        runOnAllPages(() -> {
            assertTrue(page.querySelectorAll("tr[part='row']").size() > 10);
            fillFirstName(FIRST_NAME);
            fillLastName(LAST_NAME);
            fillEmail(EMAIL);
            fillPhone(PHONE);
            fillDateOfBirth(DATE_OF_BIRTH);
            fillOccupation(OCCUPATION);
            fillImportant(IMPORTANT);
            clickSave();

            ElementHandle grid = page.querySelector("vaadin-grid");
            grid.evaluateHandle("grid => grid.scrollToIndex(9999)");
            // Not very pretty
            page.waitForSelector("vaadin-grid-cell-content >> 'Bob'");

            assertNotNull(getGridCell(FIRST_NAME));
            assertNotNull(getGridCell(LAST_NAME));
            assertNotNull(getGridCell(EMAIL));
            assertNotNull(getGridCell(PHONE));
            // TODO: assertNotNull(getGridCell(DATE_OF_BIRTH.toString()));
            assertNotNull(getGridCell(OCCUPATION));
            // TODO: Check important?
        });
    }

    private void runOnAllPages(Runnable runnable) {
        for(String menuItem: MENU_ITEMS) {
            page.click("a >> '" + menuItem + "'");
            runnable.run();
        }
    }

    private void fillFirstName(String firstName) {
        page.type("*css=vaadin-text-field >> 'First Name'", firstName);
    }

    private void fillLastName(String lastName) {
        page.type("*css=vaadin-text-field >> 'Last Name'", lastName);
    }

    private void fillEmail(String email) {
        page.type("*css=vaadin-text-field >> 'Email'", email);
    }

    private void fillPhone(String phone) {
        page.type("*css=vaadin-text-field >> 'Phone'", phone);
    }

    private void fillDateOfBirth(LocalDate dateOfBirth) {
        page.type("*css=vaadin-date-picker >> 'Date Of Birth'", dateOfBirth.toString());
        page.press("*css=vaadin-date-picker >> 'Date Of Birth'", "Enter");
    }

    private void fillOccupation(String occupation) {
        page.type("*css=vaadin-text-field >> 'Occupation'", occupation);
    }

    private void fillImportant(boolean important) {
        if (important) {
            page.click("*css=vaadin-checkbox >> 'Important'");
        }
    }

    private void clickSave() {
        page.click("*css=vaadin-button >> 'Save'");
    }

    private ElementHandle getGridCell(String content) {
        return page.querySelector("vaadin-grid-cell-content >> '" + content + "'");
    }
}
