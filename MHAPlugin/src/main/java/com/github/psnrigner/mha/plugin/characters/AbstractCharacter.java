package com.github.psnrigner.mha.plugin.characters;

import com.github.psnrigner.mha.common.SpoilLevel;
import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.github.psnrigner.mha.plugin.disguises.Skin;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCharacter
{
    private final Skin skin;
    private final Belief belief;
    private final SpoilLevel spoilLevel;
    private final Map<Integer, AbstractSkill> skills;

    protected AbstractCharacter(Skin skin, Belief belief, SpoilLevel spoilLevel)
    {
        this.skin = skin;
        this.belief = belief;
        this.spoilLevel = spoilLevel;
        this.skills = new HashMap<>();
    }

    protected void registerSkill(int slot, AbstractSkill skill)
    {
        this.skills.put(slot, skill);
    }

    public Skin getSkin()
    {
        return this.skin;
    }

    public SpoilLevel getSpoilLevel()
    {
        return this.spoilLevel;
    }

    public Map<Integer, AbstractSkill> getSkills()
    {
        return this.skills;
    }

    void setPlugin(MHAPlugin plugin)
    {
        this.skills.values().forEach(skill -> skill.setPlugin(plugin));
    }
}
