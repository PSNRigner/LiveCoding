package com.github.psnrigner.mha.plugin.manager;

import com.github.psnrigner.mha.plugin.game.MHAPlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager
{
    private final Map<UUID, MHAPlayer> players;

    public PlayerManager()
    {
        this.players = new ConcurrentHashMap<>();
    }

    public MHAPlayer getPlayer(UUID uuid)
    {
        return this.players.get(uuid);
    }

    public Map<UUID, MHAPlayer> getPlayers()
    {
        return this.players;
    }

    public Map<UUID, MHAPlayer> getInGamePlayers()
    {
        Map<UUID, MHAPlayer> inGamePlayers = new HashMap<>();

        this.players.forEach((uuid, player) ->
        {
            if (true /* TODO !player.isSpectator()*/)
            {
                inGamePlayers.put(uuid, player);
            }
        });

        return inGamePlayers;
    }

    public void onLogin(UUID uuid)
    {
        this.players.put(uuid, new MHAPlayer(uuid));
    }

    public void onLogout(UUID uuid)
    {
        MHAPlayer mhaPlayer = this.players.remove(uuid);

        if (mhaPlayer != null)
        {
            mhaPlayer.setPlayer(null);
        }
    }
}
