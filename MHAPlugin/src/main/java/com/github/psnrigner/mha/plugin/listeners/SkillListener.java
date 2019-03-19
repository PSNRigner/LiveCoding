package com.github.psnrigner.mha.plugin.listeners;

import com.github.psnrigner.mha.plugin.MHAPlugin;
import com.github.psnrigner.mha.plugin.characters.AbstractCharacter;
import com.github.psnrigner.mha.plugin.characters.AbstractSkill;
import com.github.psnrigner.mha.plugin.game.MHAPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class SkillListener implements Listener
{
    private final MHAPlugin plugin;

    public SkillListener(MHAPlugin plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        if ((event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) && event.getHand() == EquipmentSlot.HAND && this.plugin.getGame().isStarted())
        {
            MHAPlayer mhaPlayer = this.plugin.getPlayerManager().getPlayer(event.getPlayer().getUniqueId());

            AbstractCharacter character = mhaPlayer.getCharacter();

            if (character != null)
            {
                AbstractSkill skill = character.getSkills().get(event.getPlayer().getInventory().getHeldItemSlot());

                if (skill != null)
                {
                    event.setCancelled(true);
                    skill.onUse(mhaPlayer);
                }
            }
        }
    }
}
