/**
 * Read the MinesPerm class for why you don't see command perms here :(
 */

package com.boyninja1555.mines.lib;

public enum MinesPermType {
    ROLE("roles"),
    FLAG("flags");

    public final String id;

    MinesPermType(String id) {
        this.id = id;
    }
}
