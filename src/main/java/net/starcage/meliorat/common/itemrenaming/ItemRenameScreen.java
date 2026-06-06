package net.starcage.meliorat.common.itemrenaming;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.PacketDistributor;
import net.starcage.meliorat.network.RenameItemPayload;

public class ItemRenameScreen extends Screen {
    private EditBox nameBox;
    private Button doneButton;
    private final String existingName;

    public ItemRenameScreen(
            String existingName
    ) {

        super(
                Component.literal(
                        "Rename Item"
                )
        );

        this.existingName = existingName;
    }

    @Override
    protected void init() {

        int centerX = this.width / 2;

        this.nameBox =
                new EditBox(
                        this.font,
                        centerX - 100,
                        this.height / 2 - 10,
                        200,
                        20,
                        Component.empty()
                );

        this.nameBox.setValue(
                this.existingName
        );

        this.nameBox.setCursorPosition(
                this.existingName.length()
        );

        this.nameBox.setMaxLength(50);

        this.addRenderableWidget(
                this.nameBox
        );

        this.doneButton =
                Button.builder(
                                Component.literal("Done"),
                                button -> {

                                    String text =
                                            this.nameBox.getValue()
                                                    .trim();

                                    if (text.isBlank()) {
                                        return;
                                    }

                                    PacketDistributor.sendToServer(
                                            new RenameItemPayload(
                                                    text
                                            )
                                    );

                                    this.onClose();
                                }
                        )
                        .bounds(
                                centerX - 40,
                                this.height / 2 + 20,
                                80,
                                20
                        )
                        .build();

        this.addRenderableWidget(
                this.doneButton
        );

        this.setFocused(
                this.nameBox
        );
    }

    @Override
    public void render(
            GuiGraphics graphics,
            int mouseX,
            int mouseY,
            float partialTick
    ) {

        this.renderBackground(
                graphics,
                mouseX,
                mouseY,
                partialTick
        );

        graphics.drawCenteredString(
                this.font,
                this.title,
                this.width / 2,
                this.height / 2 - 40,
                0xFFFFFF
        );

        super.render(
                graphics,
                mouseX,
                mouseY,
                partialTick
        );
    }

    @Override
    public boolean keyPressed(
            int keyCode,
            int scanCode,
            int modifiers
    ) {

        if (keyCode == 257 || keyCode == 335) {

            this.doneButton.onPress();

            return true;
        }

        return super.keyPressed(
                keyCode,
                scanCode,
                modifiers
        );
    }
}
