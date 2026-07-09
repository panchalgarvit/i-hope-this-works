package com.r41ny.combatmod.client;

import com.r41ny.combatmod.client.gui.TargetScreen;
import com.r41ny.combatmod.client.logic.AimLogic;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class CombatModClient implements ClientModInitializer {
    private static KeyMapping targetKey;

    @Override
    public void onInitializeClient() {
        // Using the 3-arg constructor to avoid "Category" enum ambiguity in Mojang mappings
        targetKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
            "key.combatmod.select_target",
            GLFW.GLFW_KEY_K,
            "category.combatmod.combat"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            
            while (targetKey.consumeClick()) {
                client.setScreen(new TargetScreen());
            }
            
            AimLogic.tick(client);
        });
    }
}
