package com.epam.atm.models;

public class User {
    private static final String ACCOUNT_NAME = "cfoxtrot";
    private static final String DOMAIN_NAME = "@inbox.ru";
    private static final String PASSWORD = "n5pYZu5dmeqHVzp";

    public String getAccountName(){
        return ACCOUNT_NAME;
    }

    public String getDomainName(){
        return DOMAIN_NAME;
    }

    public String getPassword(){
        return PASSWORD;
    }
}
