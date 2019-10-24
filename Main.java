package gitlet;

import static gitlet.Utils.error;

import java.io.IOException;


/**
 * Driver class for Gitlet, the tiny stupid version-control system.
 *
 * @author Anastasia Scott
 */
public class Main {

    /**
     * Usage: java gitlet.Main ARGS, where ARGS contains
     * <COMMAND> <OPERAND> ....
     */
    public static void main(String... args) throws IOException {

        if (args.length == 0) {
            System.out.println("Please enter a command.");
        }

        switch (args[0]) {
            case "init": //done
                SetUp.init();
                break;
            case "add": //done
                Staging.add(args[1]);
                break;
            case "commit": //done
                if (args.length < 2) {
                    System.out.println("Please enter a commit message.");
                    return;
                }
                Commit.commit(args[1]);
                break;
            case "rm": //done
                Staging.remove(args[1]); //done
                break;
            case "log": //done
                Print.log();
                break;
            case "global-log": //done
                Print.globalLog();
                break;
            case "find": //done
                Print.find(args[1]);
                break;
            case "status":
                Print.status();
                break;
            case "checkout":
                if (args.length == 1) {
                    Staging.checkout(args[1]);
                }
                if (args.length == 2 && args[1].equals("--")) {
                    Staging.checkout(args[1], args[2]);
                }
                if (args.length == 3 && args[2].equals("--")) {
                    Staging.checkout(args[1], args[2], args[3]);
                }
                break;
            case "branch": //done
                Branch.branch(args[1]);
                break;
            case "rm-branch": //done
                Branch.removeBranch(args[1]);
                break;
            case "reset":
                SetUp.reset(args[1]);
                break;
            case "merge":
                Branch.merge(args[1]);
                break;
            default:
                throw error("No command with that name exists.");
        }

        //Don't forget to deal with the remaining two errors:
        //Incorrect operands.
        //Not in an initialized Gitlet directory.
    }
}
