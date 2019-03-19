package com.github.psnrigner.mha.plugin.characters;

public abstract class AbstractUltimate extends AbstractSkill
{
    protected AbstractUltimate(int id, String name, long animationTime, long generalCooldown)
    {
        super(id, name, -1L, animationTime, generalCooldown);
    }
}
