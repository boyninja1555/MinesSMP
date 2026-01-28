/**
 * Read the MinesPerm class for why you don't see command perms here :(
 */

package com.boyninja1555.mines.lib;

public enum MinesPerms {
    FLAG_BYPASS_SERVER_CLOSED(MinesPerm.create(MinesPermType.FLAG, "bypass-server-closed").value());

    public final String value;

    MinesPerms(String value) {
        this.value = value;
    }
}
