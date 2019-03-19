package com.github.psnrigner.mha.plugin.characters.deku;

import com.github.psnrigner.mha.plugin.characters.AbstractSkill;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;

class SaintLouisSmashSkill extends AbstractSkill
{
    SaintLouisSmashSkill()
    {
        super(0x02, "skill.deku.saint_louis_smash", 160L, 30L, 20L);
    }

    @Override
    protected void render(MHAPlayer player, int frame)
    {
        // TODO Render
    }
}
