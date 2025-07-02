package fr.dams4k.crackedwhitelist;

import com.mojang.logging.LogUtils;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CrackedWhitelist.MODID)
public class CrackedWhitelist  {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "crackedwhitelist";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
}
