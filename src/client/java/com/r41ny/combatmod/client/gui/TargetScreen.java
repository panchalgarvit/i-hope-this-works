package com.r41ny.combatmod.client.gui;

import com.r41ny.combatmod.client.logic.AimLogic;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import java.util.Collection;

public class TargetScreen extends Screen {
    public TargetScreen() {
        super(Component.literal("Select Target"));
    }

    @Override
    protected void init() {
        if (this.minecraft == null || this.minecraft.getConnection() == null) return;
        
        Collection<PlayerInfo> players = this.minecraft.getConnection().getOnlinePlayers();
        int yOffset = 40;
        int buttonWidth = 200;
        int centerX = this.width / 2 - buttonWidth / 2;

        for (PlayerInfo info : players) {
            // Using record-style accessors id() and name() for Mojang Mappings 1.21.x
            if (this.minecraft.player != null && info.getProfile().id().equals(this.minecraft.player.getUUID())) continue;
            
            String name = info.getProfile().name();
            this.addRenderableWidget(Button.builder(Component.literal(name), button -> {
                AimLogic.setTarget(info.getProfile().id());
                this.onClose();
            }).bounds(centerX, yOffset, buttonWidth, 20).build());
            
            yOffset += 25;
            if (yOffset > height - 60) break;
        }

        this.addRenderableWidget(Button.builder(Component.literal("Clear Target"), button -> {
            AimLogic.setTarget(null);
            this.onClose();
        }).bounds(centerX, height - 30, buttonWidth, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(this.font, this.title, this.width / 2, 20, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
