package Tests;

import Tests.enums.MarketAlertUmStates;
import main.EventLog;
import main.api.requests.ApiRequests;
import main.logSystem.WebOperations;
import TestV2.webOperations.enums.WebStates;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MarketAlertUmTests implements FsmModel {
    //Linking the required system for testing
    private ApiRequests systemUnderTestApi = new ApiRequests();
    private WebOperations systemUnderTestOperations = new WebOperations();

    //State Variables
    private MarketAlertUmStates modelState = MarketAlertUmStates.LOGGED_OFF;
    private boolean isLoggedInOnWebsite = false;
    private int eventLogType = 100;

    //Method implementations
    public MarketAlertUmStates getState() {
        return modelState;
    }

    public void reset(final boolean reset) {
        if (reset) {
            systemUnderTestApi = new ApiRequests();
            systemUnderTestOperations = new WebOperations();
        }
        modelState = MarketAlertUmStates.LOGGED_OFF;
        isLoggedInOnWebsite = false;
        int eventLogType = 100;
        systemUnderTestOperations.logOut();
    }

    //Transitions
    public boolean logInGuard() {
        return getState().equals(WebStates.LOGGED_OFF);
    }
    public @Action void logIn() {
        //Updating system under test
        systemUnderTestOperations.logOn();

        //Updating model
        modelState = MarketAlertUmStates.LOGGED_ON;
        isLoggedInOnWebsite = true;

        //Assertion to check results
        assertEquals("Alert has not been successfully posted", isLoggedInOnWebsite, systemUnderTestApi.getLog().isLoggedInOnWebsite);
    }

    public boolean viewAlertGuard() {
        return !getState().equals(WebStates.LOGGED_OFF);
    }
    public @Action void viewAlert() {
        //Updating system under test
        systemUnderTestOperations.viewAlerts();
        EventLog[] logArray = systemUnderTestApi.getEventLog();
        int actualEventLogType = logArray[logArray.length-1].eventLogType;

        //Updating model
        modelState = MarketAlertUmStates.VIEWING_ALERTS;
        eventLogType = 7;

        //Assertion to check results
        assertEquals("All alerts have not been successfully deleted", eventLogType, actualEventLogType);
    }

    public boolean postAlertGuard() {
        return !getState().equals(WebStates.LOGGED_OFF);
    }
    public @Action void postAlert() {
        //Updating system under test
        String mockJson = "{\"alertType\":6,\"heading\":\"Gigaset GL390 GSM Dual-Sim Mobile Phone (Large Buttons and SOS Function)\",\"description\":\"Easy to use GSM phone for the elderly with comfort features and SOS-function.\",\"url\":\"https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-\",\"imageUrl\":\"https://s3-eu-west-1.amazonaws.com/klk-website/upload/product/26660/gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-1662468849-1.png\",\"postedBy\":\"afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4\",\"priceInCents\":5595}";
        systemUnderTestApi.postAlert(mockJson);
        EventLog[] logArray = systemUnderTestApi.getEventLog();
        int actualEventLogType = logArray[logArray.length-1].eventLogType;

        //Updating model
        modelState = MarketAlertUmStates.ALERT_POSTED;
        eventLogType = 0;

        //Assertion to check results
        assertEquals("Alert has not been successfully posted", eventLogType, actualEventLogType);
    }

    public boolean deleteAlertsGuard() {
        return !getState().equals(WebStates.LOGGED_OFF);
    }
    public @Action void deleteAlerts() {
        //Updating system under test
        systemUnderTestApi.deleteAlerts();
        EventLog[] logArray = systemUnderTestApi.getEventLog();
        int actualEventLogType = logArray[logArray.length-1].eventLogType;

        //Updating model
        modelState = MarketAlertUmStates.ALERT_DELETED;
        eventLogType = 1;

        //Assertion to check results
        assertEquals("All alerts have not been successfully deleted", eventLogType, actualEventLogType);
    }

    public boolean logOffGuard() {
        return !getState().equals(WebStates.LOGGED_OFF);
    }
    public @Action void logOff() {
        //Updating system under test
        systemUnderTestOperations.logOut();

        //Updating model
        modelState = MarketAlertUmStates.LOGGED_OFF;
        isLoggedInOnWebsite = false;

        //Assertion to check results
        assertEquals("Log off failed", isLoggedInOnWebsite, systemUnderTestApi.getLog().isLoggedInOnWebsite);
    }

    //Test runner
    @Test
    public void MarketAlertUmModelRunner() {
        final GreedyTester tester = new GreedyTester(new MarketAlertUmTests());
        tester.setRandom(new Random());
        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(100);
        tester.printCoverage();
    }
}
