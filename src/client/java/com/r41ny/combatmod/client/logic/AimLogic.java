package com.r41ny.combatmod.client.logic;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import java.util.UUID;

public class AimLogic {
    private static UUID targetUUID = null;

    public static void setTarget(UUID uuid) {
        targetUUID = uuid;
    }

    public static void tick(Minecraft client) {
        if (client.player == null || client.level == null || targetUUID == null || client.screen != null) return;

        Player target = null;
        for (Player entity : client.level.players()) {
            if (entity.getUUID().equals(targetUUID)) {
                target = entity;
                break;
            }
        }

        if (target != null && target.isAlive() && !target.isInvisible()) {
            lockAim(client.player, target);
            handleShieldStatus(client.player, target);
        } else {
            targetUUID = null;
        }
    }

    private static void lockAim(LocalPlayer player, Player target) {
        Vec3 targetEyePos = target.getEyePosition();
        Vec3 playerEyePos = player.getEyePosition();

        double dx = targetEyePos.x - playerEyePos.x;
        double dy = (targetEyePos.y - 0.15) - playerEyePos.y;
        double dz = targetEyePos.z - playerEyePos.z;

        double distanceXZ = Math.sqrt(dx * dx + dz * dz);

        float targetYaw = Mth.wrapDegrees((float) Math.toDegrees(Math.atan2(dz, dx)) - 90.0F);
        float targetPitch = Mth.wrapDegrees((float) (-Math.toDegrees(Math.atan2(dy, distanceXZ))));

        player.setYRot(targetYaw);
        player.setXRot(targetPitch);
    }

    private static void handleShieldStatus(LocalPlayer player, Player target) {
        if (target.isBlocking()) {
            int axeSlot = -1;
            for (int i = 0; i < 9; i++) {
                if (player.getInventory().getItem(i).getItem() instanceof AxeItem) {
                    axeSlot = i;
                    break;
                }
            }

            if (axeSlot != -1 && player.getInventory().selected != axeSlot) {
                player.getInventory().selected = axeSlot;
            }
        }
    }
}
