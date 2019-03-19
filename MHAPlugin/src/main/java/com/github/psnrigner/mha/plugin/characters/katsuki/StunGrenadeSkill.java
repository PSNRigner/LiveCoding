package com.github.psnrigner.mha.plugin.characters.katsuki;

import com.github.psnrigner.mha.plugin.characters.AbstractSkill;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;

class StunGrenadeSkill extends AbstractSkill
{
    StunGrenadeSkill()
    {
        super(0x11, "skill.katsuki.stun_grenade", 160L, 80L, 10L);
    }

    @Override
    protected void render(MHAPlayer player, int frame)
    {
        // TODO Render
    }
}
