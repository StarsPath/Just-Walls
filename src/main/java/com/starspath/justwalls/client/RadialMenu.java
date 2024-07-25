package com.starspath.justwalls.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.starspath.justwalls.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Vector4f;

import static net.minecraft.util.Mth.TWO_PI;

public class RadialMenu extends Screen {
    private final Vector4f radialButtonColor = new Vector4f(0f, 0f, 0f, .5f);
    private final Vector4f selectedColor = new Vector4f(0f, .5f, 1f, .5f);
    private static float PRECISION = 2.5f / 360.0f;
    private static int INNER_RADIUS = 50;
    private static int OUTER_RADIUS = 100;
    private static int SEGMENTS = 6;

    private final int whiteTextColor = 0xffffffff;

    public RadialMenu(){
        super(Component.literal("Radial Menu Component Literal"));
    }

    public RadialMenu(Component component) {
        super(component);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);

        // Draw a circle in the middle of the screen
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        drawCircle(centerX, centerY); // ARGB format: Alpha, Red, Green, Blue
        int selection = getMouseSelection(centerX, centerY, mouseX, mouseY);
        if(selection > -1){
            drawSelection(centerX, centerY, selection);
        }
    }

    private void drawSelection(int centerX, int centerY, int selection){
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        float s4 = (float) getAngleFor(selection - 0.5, SEGMENTS);
        float e4 = (float) getAngleFor(selection + 0.5, SEGMENTS);
        drawPieArc(buffer, centerX, centerY, 0, INNER_RADIUS, OUTER_RADIUS, s4, e4, new Vector4f(1, 0, 0, 1));

        tessellator.end();

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }


    private void drawCircle(int centerX, int centerY) {
        RenderSystem.enableBlend();
        RenderSystem.disableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder buffer = tessellator.getBuilder();
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);


        float s = (float) getAngleFor(0 - 0.5, 1);
        float e = (float) getAngleFor(0 + 0.5, 1);
        drawPieArc(buffer, centerX, centerY, 0, INNER_RADIUS, OUTER_RADIUS, s, e, radialButtonColor);

        float s2 = (float) getAngleFor(0 - 0.5, SEGMENTS);
        float e2 = (float) getAngleFor(0 + 0.5, SEGMENTS);
        drawPieArc(buffer, centerX, centerY, 0, INNER_RADIUS, OUTER_RADIUS, s2, e2, selectedColor);

        float s3 = (float) getAngleFor(2 - 0.5, SEGMENTS);
        float e3 = (float) getAngleFor(2 + 0.5, SEGMENTS);
        drawPieArc(buffer, centerX, centerY, 0, INNER_RADIUS, OUTER_RADIUS, s3, e3, selectedColor);

        float s4 = (float) getAngleFor(4 - 0.5, SEGMENTS);
        float e4 = (float) getAngleFor(4 + 0.5, SEGMENTS);
        drawPieArc(buffer, centerX, centerY, 0, INNER_RADIUS, OUTER_RADIUS, s4, e4, selectedColor);

        tessellator.end();

        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private int getMouseSelection(int centerX, int centerY, int mouseX, int mouseY){
        int deltaX = mouseX - centerX;
        int deltaY = mouseY - centerY;
        double angleRadians = Math.atan2(deltaY, deltaX);
        if (angleRadians < 0) {
            angleRadians += 2 * Math.PI;
        }

        double angleDegrees = Math.toDegrees(angleRadians);

        int selection = -1;
        for(int i = 0; i < SEGMENTS; i++){
            float s = (float) Math.toDegrees(getAngleFor(i - 0.5, SEGMENTS)) % 360;
            float e = (float) Math.toDegrees(getAngleFor(i + 0.5, SEGMENTS));
            if(e > 360)
                e %= 360;
            if(angleDegrees > s && angleDegrees < e){
                selection = i;
            }
//            Utils.debug(i, s, e, angleDegrees, angleDegrees > s && angleDegrees < e);
        }
        return selection;
    }

    @Override
    public boolean mouseClicked(double p_94695_, double p_94696_, int p_94697_) {
        return super.mouseClicked(p_94695_, p_94696_, p_94697_);
    }

    private void drawPieArc(BufferBuilder buffer, float x, float y, float z, float radiusIn, float radiusOut, float startAngle, float endAngle, Vector4f color) {
        float angle = endAngle - startAngle;
        int sections = Math.max(1, Mth.ceil(angle / PRECISION));

        angle = endAngle - startAngle;

        int r = (int)(color.x * 255);
        int g = (int)(color.y * 255);
        int b = (int)(color.z * 255);
        int a = (int)(color.w * 255);

        for (int i = 0; i < sections; i++) {
            float angle1 = startAngle + (i / (float) sections) * angle;
            float angle2 = startAngle + ((i + 1) / (float) sections) * angle;

            float pos1InX = x + radiusIn * (float) Math.cos(angle1);
            float pos1InY = y + radiusIn * (float) Math.sin(angle1);
            float pos1OutX = x + radiusOut * (float) Math.cos(angle1);
            float pos1OutY = y + radiusOut * (float) Math.sin(angle1);
            float pos2OutX = x + radiusOut * (float) Math.cos(angle2);
            float pos2OutY = y + radiusOut * (float) Math.sin(angle2);
            float pos2InX = x + radiusIn * (float) Math.cos(angle2);
            float pos2InY = y + radiusIn * (float) Math.sin(angle2);

            buffer.vertex(pos1OutX, pos1OutY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos1InX, pos1InY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos2InX, pos2InY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos2OutX, pos2OutY, z).color(r, g, b, a).endVertex();
        }
    }

    private double getAngleFor(double i, int numItems) {
        if (numItems == 0)
            return 0;
        double angle = ((i / numItems) + 0.25) * TWO_PI + Math.PI;
        return angle;
    }
}