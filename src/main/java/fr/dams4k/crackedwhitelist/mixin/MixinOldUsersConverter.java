package fr.dams4k.crackedwhitelist.mixin;

import com.google.common.io.Files;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.yggdrasil.ProfileNotFoundException;
import com.mojang.logging.LogUtils;
import fr.dams4k.crackedwhitelist.CrackedWhitelist;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.server.players.UserWhiteListEntry;
import net.minecraft.world.entity.player.Player;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

@Mixin(OldUsersConverter.class)
public class MixinOldUsersConverter {
    @Unique
    private static final File OFFLINE_WHITELIST_FILE = new File(PlayerList.WHITELIST_FILE.getParentFile() ,"offline_" + PlayerList.WHITELIST_FILE.getName());

    @Shadow static final Logger LOGGER = LogUtils.getLogger();

    @Overwrite
    public static boolean convertWhiteList(final MinecraftServer minecraftServer) {
        File whitelistFile = minecraftServer.usesAuthentication() ? PlayerList.WHITELIST_FILE : OFFLINE_WHITELIST_FILE;
        CrackedWhitelist.LOGGER.info("The whitelist used is " + whitelistFile.getPath() + " because " + String.valueOf(minecraftServer.usesAuthentication()));

        final UserWhiteList userwhitelist = new UserWhiteList(whitelistFile);
        if (OldUsersConverter.OLD_WHITELIST.exists() && OldUsersConverter.OLD_WHITELIST.isFile()) {
            if (userwhitelist.getFile().exists()) {
                try {
                    userwhitelist.load();
                } catch (IOException ioexception1) {
                    LOGGER.warn("Could not load existing file {}", userwhitelist.getFile().getName(), ioexception1);
                }
            }

            try {
                List<String> list = Files.readLines(OldUsersConverter.OLD_WHITELIST, StandardCharsets.UTF_8);
                ProfileLookupCallback profilelookupcallback = new ProfileLookupCallback() {
                    public void onProfileLookupSucceeded(GameProfile p_11143_) {
                        minecraftServer.getProfileCache().add(p_11143_);
                        userwhitelist.add(new UserWhiteListEntry(p_11143_));
                    }

                    public void onProfileLookupFailed(GameProfile p_11140_, Exception p_11141_) {
                        LOGGER.warn("Could not lookup user whitelist entry for {}", p_11140_.getName(), p_11141_);
                        if (!(p_11141_ instanceof ProfileNotFoundException)) {
//                            throw new OldUsersConverter.ConversionError("Could not request user " + p_11140_.getName() + " from backend systems", p_11141_);
                        }
                    }
                };
                lookupPlayers(minecraftServer, list, profilelookupcallback);
                userwhitelist.save();
                renameOldFile(OldUsersConverter.OLD_WHITELIST);
                return true;
            } catch (IOException ioexception) {
                LOGGER.warn("Could not read old whitelist to convert it!", (Throwable) ioexception);
                return false;
            }
//            } catch (MixinOldUsersConverter.MixinConversionError oldusersconverter$conversionerror) {
//                LOGGER.error("Conversion failed, please try again later", (Throwable)oldusersconverter$conversionerror);
//                return false;
//            }
        } else {
            return true;
        }
    }

    @Shadow
    protected static void lookupPlayers(MinecraftServer p_11087_, Collection<String> p_11088_, ProfileLookupCallback p_11089_) {
        /* dummy */
    }

    @Shadow
    protected static void renameOldFile(File file) {
        /* dummy */
    }
}
