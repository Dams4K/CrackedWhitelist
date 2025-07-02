package fr.dams4k.crackedwhitelist.mixin;

import com.mojang.authlib.GameProfile;
import fr.dams4k.crackedwhitelist.CrackedWhitelist;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.players.StoredUserList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.io.File;

@Mixin(UserWhiteList.class)
public abstract class MixinUserWhiteList extends StoredUserList<GameProfile, UserWhiteListEntry> {
    public MixinUserWhiteList(File file) {
        super(file);
    }

    /**
     * @author Dams4K
     * @reason A new file is used to store whitelisted players to not override the existing whitelist.json file
     */
    @ModifyVariable(method = "<init>", at = @At("LOAD"))
    private static File injectFile(File file) {
        return new File(file.getParentFile(), "cracked_" + file.getName());
    }

    /**
     * @author Dams4K
     * @reason getKeyForUser is used to get the uuid of the profile. Leading me to returning the offline uuid
     */
    @Overwrite
    protected String getKeyForUser(GameProfile gameProfile) {
        String playerName = gameProfile.getName();
        return UUIDUtil.createOfflinePlayerUUID(playerName).toString();
    }
}
