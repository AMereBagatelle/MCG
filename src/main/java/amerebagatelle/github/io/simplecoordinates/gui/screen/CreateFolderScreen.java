package amerebagatelle.github.io.simplecoordinates.gui.screen;

import amerebagatelle.github.io.simplecoordinates.SimpleCoordinates;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.LiteralText;

import java.io.IOException;

public class CreateFolderScreen extends Screen {
    private TextFieldWidget nameWidget;
    private CoordinatesScreen parent;

    public CreateFolderScreen(CoordinatesScreen parent) {
        super(new LiteralText("CoordinateFolderScreen"));
        this.parent = parent;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        this.nameWidget = new TextFieldWidget(this.font, this.width/2-100, this.height/2-100, 200, 20, "");
        this.nameWidget.setText("Untitled");
        this.children.add(nameWidget);
        this.addButton(new ButtonWidget(this.width/2-100, this.height/2, 200, 20, "Create", onPress -> {
            try {
                SimpleCoordinates.coordinatesManager.createFolder(parent.coordinateFileListWidget.workingDirectory.toString() + "/" + this.nameWidget.getText());
            } catch (IOException ignored) {
            } finally {
                this.minecraft.openScreen(parent);
            }
        }));
        this.addButton(new ButtonWidget(this.width/2-100, this.height/2+30, 200, 20, "Cancel", onPress -> {
            this.minecraft.openScreen(parent);
        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        super.render(mouseX, mouseY, delta);
        this.drawCenteredString(this.font, "Folder Creation", this.width/2, this.height/2-130, 16777215);
        nameWidget.render(mouseX, mouseY, delta);
    }
}
