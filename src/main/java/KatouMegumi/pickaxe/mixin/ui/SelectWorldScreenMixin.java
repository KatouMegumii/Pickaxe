package KatouMegumi.pickaxe.mixin.ui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.world.level.storage.LevelSummary;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SelectWorldScreen.class)
public class SelectWorldScreenMixin extends Screen {
    private SelectWorldScreenMixin() {
        super(null);
    }

    @Unique
    private ButtonWidget pickaxeButton;

    @Unique
    private WorldListWidget levelList;

    @Inject(method = "init", at = @At("HEAD"))
    private void init(CallbackInfo ci) {
        this.pickaxeButton = this.addDrawableChild(
                ButtonWidget.builder(Text.of("P"), button ->
                                this.levelList.getSelectedAsOptional().ifPresent(WorldListWidget.WorldEntry::edit))
                        .dimensions(this.width / 2 - 134, 22, 20, 20)
                        .build()
        );
    }

    @Inject(method = "worldSelected", at = @At("HEAD"))
    private void worldSelected(LevelSummary levelSummary, CallbackInfo ci) {
        if(levelSummary == null) {
            this.pickaxeButton.active = false;
        }else {
            this.pickaxeButton.active = levelSummary.isEditable();
        }
    }
}


