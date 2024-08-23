package com.starspath.justwalls.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class StructureBlockHPScreen {

    private static int currentHp;
    private static int maxHp;
    private static boolean render;

    public static void setCurrentHp(int currentHp) {
        StructureBlockHPScreen.currentHp = currentHp;
    }

    public static void setMaxHp(int maxHp) {
        StructureBlockHPScreen.maxHp = maxHp;
    }

    public static void setRender(boolean render) {
        StructureBlockHPScreen.render = render;
    }

    public StructureBlockHPScreen(){

    }

    public static void renderOverlay(GuiGraphics guiGraphics) {
        if(render){
            Minecraft mc = Minecraft.getInstance();
            int screenWidth = mc.getWindow().getGuiScaledWidth();
            int screenHeight = mc.getWindow().getGuiScaledHeight();

            // Calculate the position for "Hello World" at the center top of the screen
            String message = "HP: " + currentHp + "/" + maxHp;
            int textWidth = mc.font.width(message);
            int x = (screenWidth - textWidth) / 2;
            int y = 10; // Distance from the top edge of the screen

            // Render the text
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F); // White color
            guiGraphics.drawString(mc.font, message, x, y, 0xFFFFFF); // Draw the text in white
        }
    }
}
