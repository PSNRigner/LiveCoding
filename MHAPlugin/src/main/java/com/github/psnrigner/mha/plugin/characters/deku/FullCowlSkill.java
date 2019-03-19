package com.github.psnrigner.mha.plugin.characters.deku;

import com.github.psnrigner.mha.plugin.characters.AbstractUltimate;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;

class FullCowlSkill extends AbstractUltimate
{
    FullCowlSkill()
    {
        super(0x09, "skill.deku.full_cowl", 200L, 10L);
    }

    @Override
    protected void render(MHAPlayer player, int frame)
    {
        // TODO Render
    }
}
