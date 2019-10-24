package gitlet;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static java.io.File.separator;

public class StagingUnitTests {

    @Test
    public void testAdd() {
        Staging.add("Batman");
        Staging.add("Green Arrow");
        Staging.add("Iron Man");
        Staging.add("Swamp Thing");
        System.out.println(Staging.getStgIndex());
        System.out.println(
                Utils.plainFilenamesIn(".gitlet" + separator + "staging"));
    }

    @Test
    public void testChangedAdd() {
        Staging.add("Iron Man");
        System.out.println(Staging.getStgIndex());
        System.out.println(
                Utils.plainFilenamesIn(".gitlet" + separator + "staging"));
    }

    @Test
    public void testRemove1() {
        Staging.remove("Iron Man");
        System.out.println(Staging.getStgIndex());
    }

    @Test
    public void testRemove2() throws IOException {
        Staging.remove("Batman");
        System.out.println(Staging.getStgIndex());
        Commit.commit("I'm Batman!");
        System.out.println(Commit.getHeadIndex());
    }

    @Test
    public void testPrintStgIndex() {
        Staging.getStgIndex();
    }

    @Test
    public void testGetStgIndex() {
        System.out.println(Staging.getStgIndex());
    }

    @Test
    public void testResetStaging() throws IOException {
        Staging.resetStaging();
        System.out.println(Staging.getStgIndex());
    }

    @Test
    public void testSingleFileCheckout() {
        Staging.checkout("--", "Green Arrow");
        System.out.println(Utils.plainFilenamesIn(
                Paths.get("").toAbsolutePath().toString()));
    }

    @Test
    public void testBranchCheckout() {
        Staging.checkout("--", "Unicorns and Rainbows");
    }

}