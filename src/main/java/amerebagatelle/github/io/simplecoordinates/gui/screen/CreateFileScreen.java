package amerebagatelle.github.io.simplecoordinates.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class CreateFileScreen extends Screen {
    private TextFieldWidget nameWidget;
    private CoordinatesScreen parent;

    public CreateFileScreen(CoordinatesScreen parent) {
        super(new LiteralText("CoordinateFileScreen"));
        this.parent = parent;
    }

    @Override
    public void init(MinecraftClient client, int width, int height) {
        super.init(client, width, height);
        this.nameWidget = new TextFieldWidget(this.font, this.width/2-100, this.height/2-100, 200, 20, "");
        this.nameWidget.setText("Untitled");
        this.children.add(nameWidget);
        this.addButton(new ButtonWidget(this.width/2-100, this.height/2, 200, 20, "Create", onPress -> {

        }));
        this.addButton(new ButtonWidget(this.width/2-100, this.height/2+30, 200, 20, "Cancel", onPress -> {

        }));
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        this.renderBackground();
        super.render(mouseX, mouseY, delta);
        this.drawCenteredString(this.font, "File Creation", this.width/2, this.height/2-130, 16777215);
        nameWidget.render(mouseX, mouseY, delta);
    }
}
