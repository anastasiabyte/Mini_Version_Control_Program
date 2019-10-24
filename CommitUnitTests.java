package gitlet;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;

public class CommitUnitTests {

    @Test
    public void newCommi1() throws IOException {
        Staging.add("Green Arrow");
        Commit.commit("Awesome Sauce");
    }

    @Test
    public void newCommit2() throws IOException {

        Staging.add("Swamp Thing");
        Commit.commit("Amazeballs");
    }

    @Test
    public void testGetHeadName() {
        System.out.println(Commit.getHeadName());
    }

    @Test
    public void testGetParent() {
        Commit c = new Commit("");
        System.out.println(c.getParent());
    }

    @Test
    public void testGetSecondParent() {
        Commit c = new Commit("");
        System.out.println(c.getSecondParent());
    }

    @Test
    public void testPrintCommitTime() {
        Commit c = new Commit(" ");
        System.out.println(c.getCommitTime());
    }

    @Test
    public void testPrintMessage() {
        Commit c = new Commit("I work!");
        System.out.println(c.getMessage());
    }

    @Test
    public void testGetIndex() {
        Commit c = new Commit(" ");
        System.out.println(c.getIndex());
    }

    @Test
    public void testGetParentIndex() {
        Commit c = new Commit(" ");
        System.out.println(c.getParentIndex());
    }

    @Test
    public void testDeserializationAndRead() {
        Commit commit = new Commit("this is a test");
        File testFile = new File(".gitlet" + separator
                + "commits" + separator + "testCommit");
        Utils.writeObject(testFile, commit);
        Commit test = Utils.readObject(testFile, Commit.class);
        System.out.println(test);
    }

}
