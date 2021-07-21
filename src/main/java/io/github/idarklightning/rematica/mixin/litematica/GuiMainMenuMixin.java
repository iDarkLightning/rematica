package io.github.idarklightning.rematica.mixin.litematica;


import fi.dy.masa.litematica.gui.GuiMainMenu;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import io.github.idarklightning.rematica.gui.ButtonListener;
import io.github.idarklightning.rematica.gui.MainMenuButtonType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiMainMenu.class, remap = false)
public abstract class GuiMainMenuMixin extends GuiBase {

    @Shadow
    protected abstract int getButtonWidth();

    @ModifyArg(
            method = "initGui",
            at = @At(value = "INVOKE", target = "Lfi/dy/masa/litematica/gui/GuiMainMenu;createChangeMenuButton(IIILfi/dy/masa/litematica/gui/GuiMainMenu$ButtonListenerChangeMenu$ButtonType;)V", ordinal = 6), index = 1)
    private int createSchematicManagerButton(int y) {
        return y - 22;
    }

    @Inject(method = "initGui", at = @At("RETURN"))
    private void initGui(CallbackInfo ci) {
        int width = this.getButtonWidth();
        ButtonGeneric button = new ButtonGeneric(width + 32, 74, width, 20, "Load Rematics");
        this.addButton(button, new ButtonListener(MainMenuButtonType.VIEW_REMATICS, this));
    }
}

