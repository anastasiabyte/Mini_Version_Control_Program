package gitlet;

import org.junit.Test;

import java.io.IOException;

public class PrintUnitTests {

    @Test
    public void testLog() {
        Print.log();
    }

    @Test
    public void testGlobalLog() {
        Print.globalLog();
    }

    @Test
    public void testFind() {
        Print.find("I am Batman!");
    }

    @Test
    public void prepStatus() throws IOException {
        SetUp.init();
        Staging.add("Batman");
        Staging.add("Swamp Thing");
        Commit.commit("This is it.");
        Staging.add("Green Arrow");
        Staging.remove("Swamp Thing");
    }

    @Test
    public void testStatus() {
        Print.status();
    }
}
