package gitlet;

import javax.xml.ws.Holder;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.io.File.separator;

/**
 * This class contains all methods which print out commits
 * including log, global-log, find, and status.
 *
 * @author Anastasia Scott
 */
public class Print {

    /**
     * Prints a history of all commits for the current branch.
     */
    public static void log() {

        String nextSha = Commit.getHeadSha();
        List log = Utils.plainFilenamesIn(".gitlet" + separator + "commits");
        for (int i = 0; nextSha != null; i += 1) {
            Commit commit = Utils.readObject(
                    new File(".gitlet" + separator + "commits"
                            + separator + log.get(i)), Commit.class);
            if (log.get(i).equals(nextSha)) {
                System.out.println("===");
                System.out.println("commit " + log.get(i));
                if (commit.getSecondParent() != null) {
                    System.out.println("Merge: "
                            + commit.getParent().substring(0, 6) + " "
                            + commit.getSecondParent().substring(0, 6));
                }
                System.out.println("Date: " + commit.getCommitTime());
                System.out.println(commit.getMessage() + "\n");
                nextSha = commit.getParent();
            }
            if (commit.getSecondParent() != null) {
                System.out.println("Merged development into "
                        + Branch.getBranchName() + ".");
            }
        }
    }

    /**
     * Outputs a history of all commits from all branches.
     */
    public static void globalLog() {

        List globalLog = Utils.plainFilenamesIn(".gitlet"
                + separator + "commits");
        for (int i = 0; i < globalLog.size(); i += 1) {
            Commit commit = Utils.readObject(
                    new File(".gitlet" + separator + "commits"
                            + separator + globalLog.get(i)), Commit.class);
            System.out.println("===");
            System.out.println("commit " + globalLog.get(i));
            System.out.println("Date: " + commit.getCommitTime());
            System.out.println(commit.getMessage() + "\n");
        }

    }

    /**
     * Returns a list of all commits with given log message.
     *
     * @param m message input by user.
     */
    public static void find(String m) {
        List globalLog = Utils.plainFilenamesIn(".gitlet"
                + separator + "commits" + separator);
        for (int i = 0; i < globalLog.size(); i += 1) {
            Commit commit = Utils.readObject(new File(".gitlet"
                    + separator + "commits" + separator
                    + globalLog.get(i)), Commit.class);
            if (commit.getMessage().equals(m)) {
                System.out.println("===");
                System.out.println("commit " + globalLog.get(i));
                System.out.println("Date: " + commit.getCommitTime());
                System.out.println(commit.getMessage() + "\n");

            }
        }
    }

    /**
     * Prints a snapshots of the current system status.
     */
    public static void status() {

        HashMap<String, String> stgIndex = Staging.getStgIndex();
        HashMap<String, String> headIndex = Commit.getHeadIndex();
        List<String> inWkDir = Utils.plainFilenamesIn(
                Paths.get("").toAbsolutePath().toString());
        List<String> holder = new ArrayList<>();

        System.out.println("=== Branches ===");
        System.out.println("*" + Commit.getHeadName());
        List<String> inHeads = Utils.plainFilenamesIn(
                ".gitlet" + separator + "heads");
        for (String i : inHeads) {
            if (!i.equals(Commit.getHeadName()) && !i.equals(i.startsWith("split"))) {
                System.out.println(i);
            }
        }
        System.out.println("\n");

        System.out.println("=== Staged Files ===");
        for (String i : stgIndex.keySet()) {
            if (!i.substring(0, 5).equals("remove")) {
                System.out.println(i);
            } else {
                holder.add(i);
            }
        }
        System.out.println(holder);
        System.out.println("\n");

        System.out.println("=== Removed Files ===");
        for (String i : holder) {
            System.out.println(i);
        }
        holder.clear();
        System.out.println("\n");

        System.out.println("=== Modifications Not Staged For Commit ===");
        for (String i : inWkDir) {
            byte[] iArray = Utils.readContents(
                    new File(Paths.get("").toAbsolutePath().toString()));
            if (!Utils.sha1(iArray).equals(headIndex.get(i))
                    && Utils.sha1(iArray).equals(stgIndex.get(i))) {
                System.out.println("i");
            } else if (!Utils.sha1(iArray).equals(headIndex.get(i))
                    && !Utils.sha1(iArray).equals(stgIndex.get(i))) {
                holder.add(i);
            }
        }
        System.out.println("\n");

        System.out.println("=== Untracked Files ===\n");
        for (String i : holder) {
            System.out.println(i + "\n");
        }
        System.out.println("\n");
    }
}
