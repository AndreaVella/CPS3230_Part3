package TestV2.webOperations;

import TestV2.webOperations.enums.WebStates;
import main.EventLog;
import main.api.requests.ApiRequests;
import main.logSystem.WebOperations;
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

public class WebTests implements FsmModel {
    //Linking the required system for testing
    private ApiRequests helper = new ApiRequests();
    private WebOperations systemUnderTest = new WebOperations();

    //State Variables
    private WebStates modelLog = WebStates.LOGGED_OFF;
    private boolean isLoggedInOnWebsite = false;
    private int eventLogType = 100;

    //Method implementations
    public WebStates getState() {return modelLog;}

    public void reset(final boolean reset) {
        if (reset) {
            systemUnderTest = new WebOperations();
        }
        modelLog = WebStates.LOGGED_OFF;
        isLoggedInOnWebsite = false;
        int eventLogType = 100;
    }


    //Transitions
    public boolean logInGuard() {
        return getState().equals(WebStates.LOGGED_OFF);
    }
    public @Action void logIn() {
        //Updating system under test
        systemUnderTest.logOn();

        //Updating model
        modelLog = WebStates.LOGGED_ON;
        isLoggedInOnWebsite = true;

        //Assertion to check results
        assertEquals("Alert has not been successfully posted", isLoggedInOnWebsite, helper.getLog().isLoggedInOnWebsite);
    }

    public boolean logOffGuard() {
        return getState().equals(WebStates.LOGGED_ON) || getState().equals(WebStates.VIEWING_ALERTS);
    }
    public @Action void logOff() {
        //Updating system under test
        systemUnderTest.logOut();

        //Updating model
        modelLog = WebStates.LOGGED_OFF;
        isLoggedInOnWebsite = false;

        //Assertion to check results
        assertEquals("All alerts have not been successfully deleted", isLoggedInOnWebsite, helper.getLog().isLoggedInOnWebsite);
    }

    public boolean viewAlertGuard() {
        return getState().equals(WebStates.LOGGED_ON);
    }
    public @Action void viewAlert() {
        //Updating system under test
        systemUnderTest.viewAlerts();
        EventLog[] logArray = helper.getEventLog();
        int actualEventLogType = logArray[logArray.length-1].eventLogType;

        //Updating model
        modelLog = WebStates.VIEWING_ALERTS;
        eventLogType = 7;

        //Assertion to check results
        assertEquals("All alerts have not been successfully deleted", eventLogType, actualEventLogType);
    }

    //Test runner
    @Test
    public void WebModelRunner() {
        final GreedyTester tester = new GreedyTester(new WebTests());
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