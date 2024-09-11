package exceptions;

/**
 * Thrown when "execute_script" command refers to a file inside the same file (or tries to enter before-accessed file), therefore, creating recursion
 */
public class RecursionException extends Exception {
}
