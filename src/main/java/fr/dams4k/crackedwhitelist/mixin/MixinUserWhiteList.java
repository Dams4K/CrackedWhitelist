package fr.dams4k.crackedwhitelist.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.players.UserWhiteList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(UserWhiteList.class)
public class MixinUserWhiteList {
    /**
     * @author
     * @reason
     */
    @Overwrite
    protected String getKeyForUser(GameProfile gameProfile) {
        String playerName = gameProfile.getName();
        return UUIDUtil.createOfflinePlayerUUID(playerName).toString();
    }
}
