package com.github.psnrigner.mha.plugin.characters.katsuki;

import com.github.psnrigner.mha.plugin.characters.AbstractSkill;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;

class BlastRushSkill extends AbstractSkill
{
    BlastRushSkill()
    {
        super(0x12, "skill.katsuki.blast_rush", 160L, 30L, 15L);
    }

    @Override
    protected void render(MHAPlayer player, int frame)
    {
        // TODO Render
    }
}
