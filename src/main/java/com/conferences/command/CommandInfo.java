package com.conferences.command;

public class CommandInfo {
    private String packageName;
    private String commandName;

    public CommandInfo() {}

    public CommandInfo(String packageName, String commandName) {
        setPackageName(packageName);
        setCommandName(commandName);
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

    @Override
    public String toString() {
        return packageName + "." + commandName;
    }
}
