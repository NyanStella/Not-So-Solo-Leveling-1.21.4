package net.stella.notsosololeveling.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.neoforge.network.PacketDistributor;
import net.stella.notsosololeveling.dialogue.IntroDialogue;
import net.stella.notsosololeveling.network.CompleteIntroPacket;

import java.util.List;

public class IntroDialogueScreen extends Screen{

    private final String nodeId;
    private final IntroDialogue.DialogueNode node;


    private int panelLeft;
    private int panelTop;
    private int panelRight;
    private int panelBottom;

    public IntroDialogueScreen()
    {
        this(IntroDialogue.START);
    }

    public IntroDialogueScreen(String nodeId){
        super(Component.literal("Introduction"));

        this.nodeId = nodeId;
        this.node = IntroDialogue.getNode(nodeId);
    }

    @Override
    protected void init(){
        super.init();

        int panelWidth = Math.min(400, this.width - 40);
        int panelHeight = Math.min(220, this.height - 40);

        this.panelLeft = (this.width - panelWidth) / 2;
        this.panelTop = (this.height -panelHeight) / 2;
        this.panelRight = this.panelLeft + panelWidth;
        this.panelBottom = this.panelTop + panelHeight;

        if (this.node.endsDialogue()){
            addRenderableWidget(
                    Button.builder(
                            Component.literal("Continue"),
                            button -> finishIntroduction()
                    )
                            .bounds(
                                    this.panelLeft + 20,
                                    this.panelBottom - 35,
                                    panelWidth - 40,
                                    20
                            )
                            .build()
            );
            return;
        }

        List<IntroDialogue.DialogueChoice> choices =
                this.node.choices();

        int totalButtonHeight = choices.size() * 24;
        int firstButtonY =
                this.panelBottom - totalButtonHeight - 12;

        for (int index = 0; index < choices.size(); index++){
            IntroDialogue.DialogueChoice choice =
                    choices.get(index);

            int buttonY = firstButtonY + index * 24;

            addRenderableWidget(
                    Button.builder(
                            Component.literal(choice.text()),
                            button -> openNode(
                                    choice.nextNodeId()
                            )
                    )
                            .bounds(
                                    this.panelLeft + 20,
                                    buttonY,
                                    panelWidth - 40,
                                    20
                            )
                            .build()
            );
        }
    }

    private void openNode(String nextNodeId){
        if (this.minecraft != null){
            this.minecraft.setScreen(
                    new IntroDialogueScreen(nextNodeId)
            );
        }
    }

    private void finishIntroduction(){
        if (this.minecraft != null){
            PacketDistributor.sendToServer(new CompleteIntroPacket());
            //Write packet to the server code to mark the introduction
            //as complete so players don't get this everytime they log in.
            this.minecraft.setScreen(null);
        }
    }

    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float partialTick){
        graphics.fill(
                0,
                0,
                this.width,
                this.height,
                0xAA000000
        );

        graphics.fill(
                this.panelLeft,
                this.panelTop,
                this.panelRight,
                this.panelBottom,
                0xEE10131A
        );

        graphics.fill(
                this.panelLeft,
                this.panelTop,
                this.panelRight,
                this.panelBottom,
                0xFF6A4C93
        );

        graphics.drawCenteredString(
                this.font,
                Component.literal(this.node.speaker()),
                this.width / 2,
                this.panelTop + 16,
                0xFFC9A7FF
        );

        int textLeft = this.panelLeft + 20;
        int textTop = this.panelTop + 42;
        int textWidth = this.panelRight - this.panelLeft - 40;

        List<FormattedCharSequence> lines =
                this.font.split(
                        Component.literal(this.node.text()),
                        textWidth
                );

        for (int index = 0; index < lines.size(); index++){
            graphics.drawString(
                    this.font,
                    lines.get(index),
                    textLeft,
                    textTop + index * 11,
                    0xFFF0F0F0
            );
        }

        super.render(
                graphics,
                mouseX,
                mouseY,
                partialTick
        );

    }

    @Override
    public boolean shouldCloseOnEsc(){
        return false;
    }

    @Override
    public boolean isPauseScreen(){
        return false;
    }
}
