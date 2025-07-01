package fr.dams4k.crackedwhitelist.mixin;

import com.mojang.authlib.GameProfile;
import fr.dams4k.crackedwhitelist.CrackedWhitelist;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.players.StoredUserList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(UserWhiteList.class)
public abstract class MixinUserWhiteList extends StoredUserList<GameProfile, UserWhiteListEntry> {
    public MixinUserWhiteList(File p_11380_) {
        super(p_11380_);
    }

    @ModifyVariable(method = "<init>", at = @At("LOAD"))
    private static File injectFile(File file) {
        return new File(file.getParentFile(), "offline_" + file.getName());
    }

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
