package net.cc.bypass;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientcommandsServerBypass implements ClientModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("clientcommands-server-bypass");

	@Override
	public void onInitializeClient() {
		if (FabricLoader.getInstance().isModLoaded("clientcommands")) {
			LOGGER.info("Bypass active — clientcommands server opt-in check neutralised");
		} else {
			LOGGER.warn("clientcommands not found — bypass has no effect");
		}
	}
}
