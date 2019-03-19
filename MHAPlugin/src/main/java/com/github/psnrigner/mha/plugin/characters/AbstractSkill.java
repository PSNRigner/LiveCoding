package com.github.psnrigner.mha.plugin.characters;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractSkill
{
    private final int id;
    private final String name;
    private final long cooldown;
    private final long animationTime;
    private final long generalCooldown;
    private final String[] description;

    private MHAPlugin plugin;
    private Map<MHAPlayer, Integer> currentFrames;

    protected AbstractSkill(int id, String name, long cooldown, long animationTime, long generalCooldown, String... description)
    {
        this.id = id;
        this.name = name;
        this.cooldown = cooldown;
        this.animationTime = animationTime;
        this.generalCooldown = generalCooldown;
        this.description = description;
        this.currentFrames = new ConcurrentHashMap<>();
    }

    final void setPlugin(MHAPlugin plugin)
    {
        this.plugin = plugin;
    }

    public final void tick()
    {
        Set<MHAPlayer> toRemove = new HashSet<>();

        for (Map.Entry<MHAPlayer, Integer> entry : this.currentFrames.entrySet())
        {
            if (entry.getValue() >= Math.max(this.cooldown, this.animationTime))
            {
                toRemove.add(entry.getKey());
                continue;
            }

            if (entry.getValue() < this.animationTime && entry.getKey().getPlayer() != null)
            {
                this.render(entry.getKey(), entry.getValue());
            }

            entry.setValue(entry.getValue() + 1);
        }

        toRemove.forEach(this.currentFrames::remove);
    }

    public ItemStack getItemStack(MHAPlayer player)
    {
        Integer frame = this.currentFrames.get(player);

        ItemStack itemStack = new ItemStack(frame == null ? Material.LIME_DYE : Material.INK_SAC, frame == null || frame >= this.cooldown ? 1 : (int) ((this.cooldown - frame + 19) / 20L));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(this.plugin.getTranslationManager().getTranslatedMessage(player.getUuid(), this.name));
        // TODO Add a meta for textures
        // TODO General cooldown

        List<String> description = new ArrayList<>(this.description.length + 3);

        for (String line : this.description)
        {
            description.add(ChatColor.GRAY + this.plugin.getTranslationManager().getTranslatedMessage(player.getUuid(), line));
        }

        description.add("");

        if (this instanceof AbstractUltimate)
        {
            description.add(ChatColor.RED + this.plugin.getTranslationManager().getTranslatedMessage(player.getUuid(), "skill.description.ultimate"));
        }
        else
        {
            description.add(ChatColor.GRAY + this.plugin.getTranslationManager().getTranslatedMessage(player.getUuid(), "skill.description.cooldown_" + (this.cooldown >= 20 && this.cooldown < 30 ? "single" : "plural"), this.cooldown / 20));
        }

        description.add(ChatColor.GRAY + this.plugin.getTranslationManager().getTranslatedMessage(player.getUuid(), "skill.description.time_" + (this.animationTime >= 20 && this.animationTime < 30 ? "single" : "plural"), this.animationTime / 20));

        itemMeta.setLore(description);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public final void onUse(MHAPlayer player)
    {
        Integer frame = this.currentFrames.get(player);
        if (frame != null && frame < this.cooldown)
        {
            return;
        }

        this.currentFrames.put(player, 0);
    }

    protected abstract void render(MHAPlayer player, int frame);
}
