package com.r41ny.combatmod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CombatMod implements ModInitializer {
    public static final String MOD_ID = "combat_utility_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Combat Utility Mod Initialized.");
    }
}
