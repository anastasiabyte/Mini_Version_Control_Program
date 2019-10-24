package gitlet;

import static java.io.File.separator;

import java.util.Date;
import java.util.HashMap;
import java.io.File;
import java.io.IOException;


/**
 * Methods to set up Gitlet and edit Head.
 *
 * @author Anastasia Scott
 */

public class SetUp {

    /**
     * Method to initialize Gitlet.
     *
     * @throws IOException
     */
    public static void init() throws IOException {

        File gitDirectory = new File(".gitlet");
        if (!gitDirectory.exists()) {
            gitDirectory.mkdir();
            File stagingDirectory = new File(".gitlet" + separator + "staging");
            stagingDirectory.mkdir();
            HashMap<String, String> clearStdIndex = new HashMap<>();
            File clearStdFile = new File(".gitlet" + separator
                    + "staging" + separator + "stgIndex");
            clearStdFile.createNewFile();
            Utils.writeObject(clearStdFile, clearStdIndex);
            File commitDirectory = new File(".gitlet" + separator + "commits");
            commitDirectory.mkdir();
            File blobsDirectory = new File(".gitlet" + separator + "blobs");
            blobsDirectory.mkdir();
            File headsDirectory = new File(".gitlet" + separator + "heads");
            headsDirectory.mkdir();
        } else {
            System.out.println("A Gitlet version-control "
                    + "system already exists in the current directory.");
        }

        Commit first = new Commit(new Date(0), "initial commit");
        byte[] firstShaArray = Utils.serialize(first);
        File commit1 = new File(".gitlet" + separator + "commits" + separator
                + Utils.sha1(firstShaArray));
        Utils.writeObject(commit1, first);
        File master = new File(".gitlet" + separator
                + "heads" + separator + "master");
        master.createNewFile();
        Utils.writeContents(master, Utils.sha1(firstShaArray));
        File head = new File(".gitlet" + separator + "head");
        head.createNewFile();
        Utils.writeContents(head, "master");
    }

    /**
     * @param newHead the branch to which the user is switching the head.
     */
    public static void switchHead(String newHead) {
        File branchFile = new File(".gitlet"
                + separator + "heads" + separator + newHead);
        if (!branchFile.exists()) {
            System.out.println("No such branch exists.");
            return;
        }
        if (branchFile.equals(Commit.getHeadName())) {
            System.out.println(" No need to checkout the current branch.");
            return;
        }
        String branch = Utils.readContentsAsString(branchFile);
        File head = new File(".gitlet" + separator + "head");
        Utils.writeContents(head, branch);
    }

    /**
     * Method called by RESET command.  Edits Head pointer for B branch.
     *
     * @param commitName is the commit to which the head will be reset.
     */
    public static void reset(String commitName) {

    }

}
