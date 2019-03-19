package com.github.psnrigner.mha.plugin.characters.deku;

import com.github.psnrigner.mha.common.SpoilLevel;
import com.github.psnrigner.mha.plugin.characters.AbstractCharacter;
import com.github.psnrigner.mha.plugin.characters.Belief;
import com.github.psnrigner.mha.plugin.disguises.Skin;

public class Deku extends AbstractCharacter
{
    public Deku()
    {
        super(Skin.DEKU, Belief.HERO, SpoilLevel.SEASON_1);

        this.registerSkill(1, new DetroitSmashSkill());
        this.registerSkill(2, new SaintLouisSmashSkill());

        this.registerSkill(3, new FullCowlSkill());
    }
}
