package io.github.idarklightning.rematica.util;

import fi.dy.masa.malilib.interfaces.IStringConsumer;

public class AddRepository implements IStringConsumer {

    @Override
    public void setString(String s) {
        System.out.println(s);
    }
}
