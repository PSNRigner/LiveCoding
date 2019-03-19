package com.github.psnrigner.mha.plugin.characters.deku;

import com.github.psnrigner.mha.plugin.characters.AbstractSkill;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;

class DetroitSmashSkill extends AbstractSkill
{
    DetroitSmashSkill()
    {
        super(0x01, "skill.deku.detroit_smash", 160L, 30L, 20L);
    }

    @Override
    protected void render(MHAPlayer player, int frame)
    {
        // TODO Render
    }
}
