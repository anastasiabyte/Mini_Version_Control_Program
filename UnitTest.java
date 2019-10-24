package gitlet;

import ucb.junit.textui;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The suite of all JUnit tests for the gitlet package.
 *
 * @author Anastasia Scott
 */
public class UnitTest {

    /**
     * Run the JUnit tests in the loa package. Add xxxTest.class entries to
     * the arguments of runClasses to run other JUnit tests.
     */
    public static void main(String[] ignored) {
        textui.runClasses(UnitTest.class, SetUnitTest.class,
                StagingUnitTests.class, CommitUnitTests.class,
                BranchUnitTests.class, PrintUnitTests.class);
    }

    /**
     * A dummy test to avoid complaint.
     */
    @Test
    public void placeholderTest() {
    }

}


