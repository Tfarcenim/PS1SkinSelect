package tfar.ps1skinselect;

import java.util.Map;

public interface PlayerDuck {

    BaseSkin getBaseSkin();

    void setBaseSkin(BaseSkin skin);

    Map<ClothingType,ClothingColor> getClothing();

    void setClothing(Map<ClothingType,ClothingColor> clothing);

}
