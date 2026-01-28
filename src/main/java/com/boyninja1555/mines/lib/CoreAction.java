package com.boyninja1555.mines.lib;

/**
 * I like type safety for some reason
 * Also Cloud converts and handles enums for command arguments automatically
 */
public enum CoreAction {
    RELOAD("reload"),
    REGENERATE_CONFIG("regenerate_config");

    private final String literal;

    CoreAction(String literal) {
        this.literal = literal;
    }

    public String literal() {
        return literal;
    }
}
