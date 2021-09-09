package com.conferences.handler.implementation;

import com.conferences.handler.abstraction.IEncryptor;
import org.mindrot.jbcrypt.BCrypt;

/**
 * {@inheritDoc}
 */
public class Encryptor implements IEncryptor {

    /**
     * {@inheritDoc}
     */
    @Override
    public String encrypt(String password) {
        String salt = BCrypt.gensalt();
        return BCrypt.hashpw(password, salt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean check(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
