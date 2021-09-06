package com.conferences.model;

import java.util.ArrayList;
import java.util.List;

public class CommandInfo {
    private String packageName;
    private String commandName;
    private List<String> urlParams;

    public CommandInfo() {
        urlParams = new ArrayList<>();
    }

    public CommandInfo(String packageName, String commandName) {
        this();
        setPackageName(packageName);
        setCommandName(commandName);
    }

    public CommandInfo(String packageName, String commandName, List<String> urlParams) {
        this(packageName, commandName);
        setUrlParams(urlParams);
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName.substring(0, 1).toUpperCase() + commandName.substring(1);
    }

    public List<String> getUrlParams() {
        return urlParams;
    }

    public void setUrlParams(List<String> urlParams) {
        this.urlParams = urlParams;
    }

    @Override
    public String toString() {
        return packageName + "." + commandName;
    }
}
