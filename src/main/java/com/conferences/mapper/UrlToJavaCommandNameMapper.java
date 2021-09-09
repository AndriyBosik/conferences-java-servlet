package com.conferences.mapper;

/**
 * {@inheritDoc}
 */
public class UrlToJavaCommandNameMapper implements IMapper<String, String> {

    /**
     * <p>
     *     Maps URL command name to Java class command name
     * </p>
     * @param commandName URL command name
     * @return Java class command name
     */
    @Override
    public String map(String commandName) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        while (index < commandName.length()) {
            char character = commandName.charAt(index);
            if (character != '-') {
                sb.append(character);
            } else {
                if (index + 1 < commandName.length()) {
                    sb.append(Character.toUpperCase(commandName.charAt(index + 1)));
                }
                index++;
            }
            index++;
        }
        if (sb.length() > 0) {
            char firstCharUppercase = Character.toUpperCase(sb.charAt(0));
            sb.setCharAt(0, firstCharUppercase);
        }
        return sb.toString();
    }
}
