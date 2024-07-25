package com.starspath.justwalls.client;

import com.mojang.blaze3d.platform.InputConstants;
import com.starspath.justwalls.JustWalls;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;

public final class Keybindings {
    public static final Keybindings INSTANCE = new Keybindings();

    private Keybindings(){}

    private static final String CATEGORY = "key.categories." + JustWalls.MODID;

    public final KeyMapping guiKey = new KeyMapping(
            "key." + JustWalls.MODID + ".gui_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_G, -1),
            CATEGORY
    );
}
