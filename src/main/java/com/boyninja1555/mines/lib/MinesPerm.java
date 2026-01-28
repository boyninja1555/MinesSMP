/**
 * Sadly, annotations suck so I can't use this permission generator for Cloud commands :(
 * FUCK JAVA (even tho it's my favorite language)
 */

package com.boyninja1555.mines.lib;

public record MinesPerm(MinesPermType type, String value) {
    public static final String NAMESPACE = "minesmp";

    public String value() {
        return NAMESPACE + "." + type.id + "." + value;
    }

    public static MinesPerm create(MinesPermType type, String value) {
        return new MinesPerm(type, value);
    }
}
