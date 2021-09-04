package com.conferences.handler.abstraction;

public interface IEncryptor {

    String encrypt(String password);

    boolean check(String password, String hashed);

}
