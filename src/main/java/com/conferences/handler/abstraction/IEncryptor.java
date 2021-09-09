package com.conferences.handler.abstraction;

/**
 * <p>
 *     Defines methods to process password encryption
 * </p>
 *
 * @author Andriy
 * @version 1.0
 * @since 2021/09/09
 */
public interface IEncryptor {

    /**
     * <p>
     *     Returns password hash
     * </p>
     * @param password password that should be hashed
     * @return password hashed
     */
    String encrypt(String password);

    /**
     * <p>
     *     Compares password with hashed one
     * </p>
     * @param password password to be checked
     * @param hashed hash to compare password with
     * @return true if password hash and hashed string are same, false otherwise
     */
    boolean check(String password, String hashed);
}
