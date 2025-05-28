package guard_plugin.logic;

public class Logger {

    private static final String PREFIX = "[Guard Testing] ";

    public static void log(String out) {
        System.out.println(PREFIX + out);
    }

    public static void error(String out) {
        System.out.println(PREFIX + "ERROR - " + out);
    }

    public static void printException(Exception exception) {
        Logger.error(exception.getLocalizedMessage());
        exception.printStackTrace(System.out);
    }

}
