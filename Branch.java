package gitlet;

import java.io.File;
import java.io.IOException;

import static java.io.File.separator;

/**
 * This class contains all methods relating to branch manipulations
 * including but not limited to: branch, rm-branch, and merge.
 *
 * @author Anastasia Scott
 */
public class Branch {

    /**
     * @param branchName name of the branch being created.
     */
    public static void branch(String branchName) {
        File thisBranchFile = new File(".gitlet"
                + separator + "heads" + separator + branchName);
        try {
            thisBranchFile.createNewFile();
            Utils.writeContents(thisBranchFile, Commit.getHeadSha());
        } catch (IOException e) {
            System.out.println("A branch with that name already exists.");
        }

    }

    /**
     * @returns name of the current branch.
     */
    public static String getBranchName() {
        return Utils.readContentsAsString(
                new File(".gitlet" + separator + "head"));
    }

    /**
     * @param branch is the branch as specified by the user.
     * @returns the sha-1 of the branch specified by user.
     */
    public static String getBranchSha(String branch) {
        File head = new File(".gitlet" + separator
                + "heads" + separator + branch);
        File thisBranch = new File(".gitlet" + separator
                + "heads" + separator + Utils.readContentsAsString(head));
        return Utils.readContentsAsString(thisBranch);
    }

    /**
     * Deletes specified branch.
     * Throws error if the specified branch is the current head.
     *
     * @param branchName is the branch marked by the user for deletion.
     */
    public static void removeBranch(String branchName) {

        if (Commit.getHeadName().equals(branchName)) {
            System.out.println("Cannot remove the current branch.");
        }
        File branch = new File(".gitlet" + separator
                + "heads" + separator + branchName);
        if (branch.exists()) {
            branch.delete();
        } else {
            System.out.println("A branch with that name does not exist.");
        }

    }

    /**
     * Merges sepecified branch into the current branch.
     *
     * @param mergedInBranchName is the name of the branch which
     *                           will be merged into the current branch.
     */
    public static void merge(String mergedInBranchName) {
        String currentBranch = Commit.getHeadSha();


    }

    //reference to split point
    //separate head from master
    //consider a serialized hashmap of all existing branches with sha ref

    //find shared history of split branches
    //identify which files have changed in both the master and the branch
    //concatenate files that have both changed (maybe join???) to address collisions
    //keep both non-common files
    //determine if spec requires deletion/merge of branch head into head.
}
