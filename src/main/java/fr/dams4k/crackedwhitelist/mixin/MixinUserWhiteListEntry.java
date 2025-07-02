package fr.dams4k.crackedwhitelist.mixin;

import com.google.gson.JsonObject;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.players.UserWhiteListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UserWhiteListEntry.class)
public abstract class MixinUserWhiteListEntry {
    /**
     * @author Dams4K
     * @reason I don't know how to shadow getUser() method of StoredUserEntry so i'll just inject this
     */
    @Inject(at = @At(value = "TAIL"), method = "serialize")
    protected void onSerialize(JsonObject jsonObject, CallbackInfo callbackInfo) {
        String playerName = jsonObject.get("name").getAsString();
        jsonObject.addProperty("uuid", UUIDUtil.createOfflinePlayerUUID(playerName).toString());
    }
}
