package com.github.psnrigner.mha.plugin.characters.katsuki;

import com.github.psnrigner.mha.plugin.characters.AbstractSkill;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;

class APShotSkill extends AbstractSkill
{
    APShotSkill()
    {
        super(0x13, "skill.katsuki.ap_shot", 160L, 20L, 25L);
    }

    @Override
    protected void render(MHAPlayer player, int frame)
    {
        // TODO Render
    }
}
