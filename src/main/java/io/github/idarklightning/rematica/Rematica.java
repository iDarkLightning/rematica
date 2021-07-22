package io.github.idarklightning.rematica;

import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;

public class Rematica implements ModInitializer {
    public static ArrayList<Rematic> REMATICS = Rematic.loadFromFile("rematics.json");

    @Override
    public void onInitialize() {

    }
}
