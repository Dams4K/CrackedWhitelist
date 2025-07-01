package fr.dams4k.crackedwhitelist.mixin;

import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import fr.dams4k.crackedwhitelist.CrackedWhitelist;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.StoredUserEntry;
import net.minecraft.server.players.UserWhiteListEntry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UserWhiteListEntry.class)
public abstract class MixinUserWhiteListEntry {
    /**
     * @author
     * @reason
     */
    @Inject(at = @At(value = "TAIL"), method = "serialize")
    protected void onSerialize(JsonObject jsonObject, CallbackInfo callbackInfo) {
        String playerName = jsonObject.get("name").getAsString();
        String myName = "Dams4K";

        CrackedWhitelist.LOGGER.info(myName);
        CrackedWhitelist.LOGGER.info(playerName);
        CrackedWhitelist.LOGGER.info(String.valueOf("Dams4K".equalsIgnoreCase(playerName)));
        CrackedWhitelist.LOGGER.info(UUIDUtil.createOfflinePlayerUUID("Dams4K").toString());
        CrackedWhitelist.LOGGER.info(UUIDUtil.createOfflinePlayerUUID(playerName).toString());

        jsonObject.addProperty("uuid", UUIDUtil.createOfflinePlayerUUID(playerName).toString());
    }
}
