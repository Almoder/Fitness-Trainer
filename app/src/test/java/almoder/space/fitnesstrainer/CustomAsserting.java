package almoder.space.fitnesstrainer;

import androidx.annotation.NonNull;

import org.junit.Assert;

public class CustomAsserting {

    @FunctionalInterface
    public interface Function { Object call(); }

    public static void assertException(@NonNull Function func,
                                       @NonNull Class<? extends Exception> exc) {
        Object ret = "Wrong exception!";
        try {
            ret = func.call();
        }
        catch (Exception e) {
            if (!e.getClass().equals(exc)) {
                Assert.fail(ret.toString());
            }
            else {
                return;
            }
        }
        Assert.fail("Expected: " + exc.toString() + ", but was: " + ret.toString());
    }

    private static void assertException(@NonNull Function func,
                                        @NonNull Class<? extends Exception> exc, int i) {
        try {
            func.call();
        }
        catch (Exception e) {
            if (!e.getClass().equals(exc)) {
                Assert.fail("Expecteds[" + i + "]: catch wrong Exception!");
            }
            else return;
        }
        Assert.fail("Expecteds[" + i + "]: " + exc.toString() + " wasn't thrown!");
    }

    public static void assertArrayOfExceptions(@NonNull Function[] functions,
                                               @NonNull Class<? extends Exception> expected) {
        String excMsg = "Functions array is empty!";
        if (functions.length == 0) {
            throw new ArrayIndexOutOfBoundsException(excMsg);
        }
        for (int i = 0; i < functions.length; i++) {
            assertException(functions[i], expected, i);
        }
    }

    @FunctionalInterface
    public interface Procedure { void call(); }

    public static void assertException(@NonNull Procedure proc,
                                       @NonNull Class<? extends Exception> exc) {
        try {
            proc.call();
        } catch (Exception e) {
            if (!e.getClass().equals(exc)) {
                Assert.fail("Wrong exception!");
            }
            else {
                return;
            }
        }
        Assert.fail(exc.toString() + " wasn't thrown!");
    }

    private static void assertException(@NonNull Procedure proc,
                                        @NonNull Class<? extends Exception> exc, int i) {
        try {
            proc.call();
        } catch (Exception e) {
            if (!e.getClass().equals(exc)) {
                Assert.fail("Expecteds[" + i + "]: catch wrong Exception!");
            }
            else {
                return;
            }
        }
        Assert.fail("Expecteds[" + i + "]: " + exc.toString() + " wasn't thrown!");
    }

    public static void assertArrayOfExceptions(@NonNull Procedure[] procedures,
                                               @NonNull Class<? extends Exception> expected) {
        String excMsg = "Functions array is empty!";
        if (procedures.length == 0) {
            throw new ArrayIndexOutOfBoundsException(excMsg);
        }
        for (int i = 0; i < procedures.length; i++) {
            assertException(procedures[i], expected, i);
        }
    }
}
