package net.stella.notsosololeveling.client;

import net.minecraft.client.Minecraft;
import net.stella.notsosololeveling.client.screen.IntroDialogueScreen;

public final class ClientPacketHandler {
    private ClientPacketHandler(){

    }

    public static void openIntro(){
        Minecraft minecraft = Minecraft.getInstance();

        minecraft.setScreen(
                new IntroDialogueScreen()
        );
    }
}
