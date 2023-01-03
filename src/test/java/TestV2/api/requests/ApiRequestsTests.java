package TestV2.api.requests;

import TestV2.api.requests.enums.ApiRequestsStates;
import main.EventLog;
import main.api.requests.ApiRequests;
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

public class ApiRequestsTests implements FsmModel {
    //Linking the required system for testing
    private ApiRequests systemUnderTest = new ApiRequests();

    //State Variables
    private ApiRequestsStates modelApi = ApiRequestsStates.API_START;
    private int eventLogType = 100;

    //Method implementations
    public ApiRequestsStates getState() {
        return modelApi;
    }

    public void reset(final boolean reset) {
        if (reset) {
            systemUnderTest = new ApiRequests();
        }
        modelApi = ApiRequestsStates.API_START;
        eventLogType = 100;
    }

    //Transitions
    public @Action void postAlert() {
        //Updating system under test
        String mockJson = "{\"alertType\":6,\"heading\":\"Gigaset GL390 GSM Dual-Sim Mobile Phone (Large Buttons and SOS Function)\",\"description\":\"Easy to use GSM phone for the elderly with comfort features and SOS-function.\",\"url\":\"https://www.klikk.com.mt/product/26660_gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-\",\"imageUrl\":\"https://s3-eu-west-1.amazonaws.com/klk-website/upload/product/26660/gigaset-gl390-gsm-dual-sim-mobile-phone-large-buttons-and-sos-function-1662468849-1.png\",\"postedBy\":\"afdda8c8-1fd0-4d95-bbab-22d6dc3dc5b4\",\"priceInCents\":5595}";
        systemUnderTest.postAlert(mockJson);
        EventLog[] logArray = systemUnderTest.getEventLog();
        int actualEventLogType = logArray[logArray.length-1].eventLogType;

        //Updating model
        modelApi = ApiRequestsStates.ALERT_POSTED;
        eventLogType = 0;

        //Assertion to check results
        assertEquals("Alert has not been successfully posted", eventLogType, actualEventLogType);
    }

    public @Action void deleteAlerts() {
        //Updating system under test
        systemUnderTest.deleteAlerts();
        EventLog[] logArray = systemUnderTest.getEventLog();
        int actualEventLogType = logArray[logArray.length-1].eventLogType;

        //Updating model
        modelApi = ApiRequestsStates.ALERT_DELETED;
        eventLogType = 1;

        //Assertion to check results
        assertEquals("All alerts have not been successfully deleted", eventLogType, actualEventLogType);
    }


    //Test runner
    @Test
    public void ApiRequestsModelRunner() {
        final GreedyTester tester = new GreedyTester(new ApiRequestsTests());
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