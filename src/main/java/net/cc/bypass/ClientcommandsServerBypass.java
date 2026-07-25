package net.cc.bypass;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientcommandsServerBypass implements ClientModInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger("clientcommands-server-bypass");

	@Override
	public void onInitializeClient() {
		LOGGER.info("Bypass active — Client Commands server opt-in check neutralised");
	}
}
