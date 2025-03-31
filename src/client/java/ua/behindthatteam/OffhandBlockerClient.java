package ua.behindthatteam;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OffhandBlockerClient implements ClientModInitializer {
	public static final String MOD_ID = "offhandblocker";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static KeyBinding toggleBinding;

	public static boolean isOffhandBlocked = true;

	@Override
	public void onInitializeClient() {
		LOGGER.info("Ініціалізація клієнтського моду OffhandBlocker");

		toggleBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.offhandblocker.toggle",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_F4,
				"category.offhandblocker.keybinds"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			boolean isF3Pressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), GLFW.GLFW_KEY_F3);

			if (toggleBinding.wasPressed() && !isF3Pressed) {
				isOffhandBlocked = !isOffhandBlocked;

				String logStatus = isOffhandBlocked ? "увімкнено" : "вимкнено";
				LOGGER.info("Блокування offhand " + logStatus);

				if (client.player != null) {
					String translationKey = isOffhandBlocked
							? "message.offhandblocker.enabled"
							: "message.offhandblocker.disabled";

					Text message = Text.translatable(translationKey);
					client.player.sendMessage(message, true);
				}
			}
		});
	}
}