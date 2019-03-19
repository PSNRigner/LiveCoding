package com.github.psnrigner.mha.plugin.characters;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.github.psnrigner.mha.plugin.characters.deku.Deku;
import com.github.psnrigner.mha.plugin.characters.katsuki.Katsuki;

import java.util.LinkedList;
import java.util.List;

public class CharacterManager
{
    private final MHAPlugin plugin;
    private final List<AbstractCharacter> characters;

    public CharacterManager(MHAPlugin plugin)
    {
        this.plugin = plugin;
        this.characters = new LinkedList<>();

        this.registerCharacters();

        this.characters.forEach(character -> character.setPlugin(this.plugin));
    }

    private void registerCharacters()
    {
        this.characters.add(new Deku());
        this.characters.add(new Katsuki());
        // this.characters.add(new Ochaco());
    }

    public List<AbstractCharacter> getCharacters()
    {
        return this.characters;
    }
}
