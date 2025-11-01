package tfar.ps1skinselect;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

public class ModDatagen {

    static void gather(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
        dataGenerator.addProvider(new Lang(dataGenerator));
    }

    static class Lang extends LanguageProvider {

        public Lang(DataGenerator gen) {
            super(gen, PS1SkinSelect.MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
            add(ModInit.BLOCK,"Wardrobe");
        }
    }
}
