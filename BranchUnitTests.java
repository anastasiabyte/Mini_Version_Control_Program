package gitlet;

import org.junit.Test;

import java.io.IOException;

public class BranchUnitTests {

    @Test
    public void testBranch() throws IOException {
        Branch.branch("rainbows and unicorns");
        Branch.branch("demons and fire");
    }

    @Test
    public void testGetBranchName() {
        System.out.println(Branch.getBranchName());
    }

    @Test
    public void testMerge() {
        Branch.merge("Demons and Fire");
    }

    @Test
    public void testRemoveBranch() {
        Branch.removeBranch("Rainbows and Unicorns");
    }
}
