package com.github.psnrigner.mha.plugin.characters.katsuki;

import com.github.psnrigner.mha.common.SpoilLevel;
import com.github.psnrigner.mha.plugin.characters.AbstractCharacter;
import com.github.psnrigner.mha.plugin.characters.Belief;
import com.github.psnrigner.mha.plugin.disguises.Skin;

public class Katsuki extends AbstractCharacter
{
    public Katsuki()
    {
        super(Skin.KATSUKI, Belief.HERO, SpoilLevel.SEASON_1);

        this.registerSkill(1, new StunGrenadeSkill());
        this.registerSkill(2, new BlastRushSkill());
        this.registerSkill(3, new APShotSkill());

        this.registerSkill(4, new HowitzerImpactSkill());
    }
}
