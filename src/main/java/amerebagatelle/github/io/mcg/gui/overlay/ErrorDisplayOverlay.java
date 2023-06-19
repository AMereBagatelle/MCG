package amerebagatelle.github.io.mcg.gui.overlay;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;

import java.util.LinkedList;

public class ErrorDisplayOverlay {
    public static final ErrorDisplayOverlay INSTANCE = new ErrorDisplayOverlay();
    private final LinkedList<Error> errors = new LinkedList<>();
    private final MinecraftClient client = MinecraftClient.getInstance();

    public ErrorDisplayOverlay() {
    }

    public void render(DrawContext context, int windowHeight) {
        for (int i = 0; i < errors.size(); i++) {
            int height = windowHeight - 15 - (i * 20);
            context.drawTextWithShadow(client.textRenderer, errors.get(i).getError(), 10, height, 16711680);
        }
        if (errors.size() > 10) {
            errors.removeLast();
        }
        errors.removeIf(Error::shouldRemove);
    }

    public void addError(String error) {
        this.errors.addFirst(new Error(error));
    }

    public static class Error {
        public String error;
        public int age;

        public Error(String error) {
            this.error = error;
            this.age = 0;
        }

        public String getError() {
            return error;
        }

        public boolean shouldRemove() {
            if (age > 300) {
                return true;
            } else {
                age++;
                return false;
            }
        }
    }
}
