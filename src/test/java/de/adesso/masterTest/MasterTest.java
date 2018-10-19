package de.adesso.masterTest;

import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;

import de.adesso.kicker.team.TeamServiceTest;
import de.adesso.kicker.tournament.lastmanstanding.LastManStandingServiceTest;
import de.adesso.kicker.tournament.singleelimination.SingleEliminationServiceTest;
import de.adesso.kicker.user.UserServiceTest;
import junit.framework.TestCase;
import junit.framework.TestResult;

//Run this class as JUnit test for testing a test suite
@RunWith(Suite.class)

//Enter test here for testing
@Suite.SuiteClasses({ TeamServiceTest.class, LastManStandingServiceTest.class, SingleEliminationServiceTest.class,
        UserServiceTest.class })

public class MasterTest {

    public static void printTestResult(TestCase test, TestResult result) {
        System.out.println("-------------------------------------------------------------------");
        System.out.println("Test NAME -> " + test.getName());
        System.out.println("Test NR. -> " + test.countTestCases());
        System.out.println("Detailed FUNCTION CALL -> " + test.toString());
        System.out.println("Result ID -> " + result.toString());
        System.out.println("Error COUNT -> " + result.errorCount());
        /**
         * for (int i = 0; i < result.errorCount(); i++) { System.out.println("!Error ->
         * "+ result.errors().); }
         **/
        System.out.println("Failure COUNT -> " + result.failureCount());
        /**
         * for (int i = 0; i < result.failureCount(); i++) {
         * 
         * }
         **/
        System.out.println("\nRESULT -> " + result.wasSuccessful() + "\n");
    }
}
