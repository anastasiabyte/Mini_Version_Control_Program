package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import static java.io.File.separator;

/**
 * This class contains all methods which modify the
 * staging dir including: add, remove, checkout.
 *
 * @author Anastasia Scott
 */
public class Staging {

    /**
     * @returns a copy of the hashmap stored in staging.
     * This hashmap records all files to edited,
     * added, or removed in the next commit.
     */
    public static HashMap getStgIndex() {
        return Utils.readObject(
                new File(".gitlet" + separator
                        + "staging" + separator + "stgIndex"), HashMap.class);
    }

    /**
     * @throws IOException is thrown when file cannot be created.
     */
    public static void resetStaging() throws IOException {

        List clearList = Utils.plainFilenamesIn(".gitlet"
                + separator + "staging" + separator);
        for (int i = 0; i < clearList.size(); i += 1) {
            new File(".gitlet" + separator + "staging"
                    + separator + clearList.get(i)).delete();
        }

        HashMap<String, String> clearStdIndex = new HashMap<>();
        File clearStdFile = new File(".gitlet" + separator
                + "staging" + separator + "stgIndex");
        clearStdFile.createNewFile();
        Utils.writeObject(clearStdFile, clearStdIndex);
    }

    /**
     * @param fileName name of file being added.
     */
    public static void add(String fileName) {

        try {
            Files.exists(Paths.get(fileName).toAbsolutePath());
        } catch (GitletException g) {
            System.out.println("File does not exist.");
            return;
        }

        HashMap<String, String> headIndex = Commit.getHeadIndex();
        HashMap<String, String> stgIndex = getStgIndex();
        File addBlob = new File(Paths.get(fileName)
                .toAbsolutePath().toString());
        byte[] blobArray = Utils.readContents(addBlob);

        if (headIndex.containsValue(Utils.sha1(blobArray))
                && stgIndex.containsValue("remove" + Utils.sha1(blobArray))) {
            stgIndex.replace(fileName, Utils.sha1(blobArray));
            return;
        } else if (headIndex.containsValue(Utils.sha1(blobArray))
                && stgIndex.containsKey(fileName)) {
            stgIndex.remove(fileName);
            return;
        } else if (!headIndex.containsValue(
                Utils.sha1(blobArray)) && stgIndex.containsKey(fileName)) {
            stgIndex.replace(fileName, Utils.sha1(blobArray));
            Utils.writeContents(
                    new File(".gitlet" + separator + "staging"
                            + separator + Utils.sha1(blobArray)), blobArray);
        } else if (!headIndex.containsValue(Utils.sha1(blobArray))) {
            stgIndex.put(fileName, Utils.sha1(blobArray));
            Utils.writeContents(
                    new File(".gitlet" + separator + "staging"
                            + separator + Utils.sha1(blobArray)), blobArray);
        }

        Utils.writeObject(new File(".gitlet" + separator
                + "staging" + separator + "stgIndex"), stgIndex);
    }

    /**
     * @param fileName name of the file being removed.
     */
    public static void remove(String fileName) {

        HashMap<String, String> headIndex = Commit.getHeadIndex();
        HashMap<String, String> stgIndex = getStgIndex();
        File addBlob =
                new File(Paths.get(fileName).toAbsolutePath().toString());
        byte[] blobArray = Utils.readContents(addBlob);

        if (!headIndex.containsKey(fileName)
                && !stgIndex.containsKey(fileName)) {
            System.out.println("No reason to remove the file.");
            return;
        }
        if (stgIndex.containsKey(fileName)
                && !headIndex.containsKey(fileName)) {
            stgIndex.remove(fileName, Utils.sha1(blobArray));
        }
        if (stgIndex.containsKey(fileName)
                && headIndex.containsKey(fileName)) {
            stgIndex.replace(fileName, "remove" + Utils.sha1(blobArray));
        }
        if (!stgIndex.containsKey(fileName)
                && headIndex.containsKey(fileName)) {
            stgIndex.put(fileName, "remove" + Utils.sha1(blobArray));
        }
        if (headIndex.containsKey(fileName)) {
            if (addBlob.exists()) {
                addBlob.delete();
            }
        }

        Utils.writeObject(new File(".gitlet" + separator
                + "staging" + separator + "stgIndex"), stgIndex);
    }

    /**
     * @param unused "--" marker
     * @param file   name of the file being checked out.
     */
    public static void checkout(String unused, String file) {
        HashMap<String, String> headIndex = Commit.getHeadIndex();
        File getBlob = new File(".gitlet" + separator
                + "blobs" + separator + headIndex.get(file));
        if (!getBlob.exists()) {
            System.out.println("File does not exist in that commit.");
            return;
        }
        File putBlob = new File(Paths.get("")
                .toAbsolutePath().toString() + separator + file);
        String blobName = Utils.readContentsAsString(getBlob);
        Utils.writeContents(putBlob, blobName);
    }

    /**
     * @param commit the commit hash of the
     *               version of the file being checked out.
     * @param unused "--" marker
     * @param file   name of the file being checked out.
     */
    public static void checkout(String commit, String unused, String file) {
        File getCommit = new File(".gitlet" + separator + "commits"
                + separator + commit);
        if (!getCommit.exists()) {
            System.out.println("No commit with that id exists.");
            return;
        }
        Commit givenCommit = Utils.readObject(getCommit, Commit.class);
        HashMap<String, String> givenIndex = givenCommit.getIndex();
        File getBlob = new File(".gitlet" + separator + "blobs"
                + separator + givenIndex.get(file));
        if (!getBlob.exists()) {
            System.out.println("File does not exist in that commit.");
            return;
        }
        File putBlob = new File(Paths.get("")
                .toAbsolutePath().toString() + separator + file);
        String blobName = Utils.readContentsAsString(getBlob);
        Utils.writeContents(putBlob, blobName);
    }

    /**
     * @param branch is the branch being checked out.
     * @throws IOException if a file cannot be created, throws exception.
     */
    public static void checkout(String branch) throws IOException {

        if (!Utils.readContentsAsString(new File(".gitlet"
                + separator + "heads" + separator + "head")).equals(branch)) {
            Staging.resetStaging();
        }

        Set<String> headIndex = Commit.getHeadIndex().keySet();
        SetUp.switchHead(branch);
        Set<String> branchIndex = Commit.getHeadIndex().keySet();
        Set<String> concatIndex = new HashSet(headIndex);
        concatIndex.addAll(branchIndex);

        for (String i : concatIndex) {
            if (headIndex.contains(i) && !branchIndex.contains(i)) {
                Utils.restrictedDelete(i);
            }
            if (!headIndex.contains(i) && branchIndex.contains(i)) {
                Commit commit = Utils.readObject(new File(".gitlet" + separator
                        + "commits" + separator + i), Commit.class);
                Utils.writeObject(new File(Paths.get("")
                        .toAbsolutePath().toString() + i), commit);
            }
        }
    }

}
