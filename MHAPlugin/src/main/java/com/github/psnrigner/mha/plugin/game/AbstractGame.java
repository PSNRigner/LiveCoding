package com.github.psnrigner.mha.plugin.game;

public abstract class AbstractGame
{
    private final String name;
    private final int requiredPlayers;
    private final int maxPlayers;
    private boolean started;

    protected AbstractGame(String name, int requiredPlayers, int maxPlayers)
    {
        this.name = name;
        this.requiredPlayers = requiredPlayers;
        this.maxPlayers = maxPlayers;
        this.started = false;
    }

    public String getName()
    {
        return this.name;
    }

    public final int getRequiredPlayers()
    {
        return this.requiredPlayers;
    }

    public final int getMaxPlayers()
    {
        return this.maxPlayers;
    }

    public void startGame()
    {
        this.started = true;
    }

    public boolean isStarted()
    {
        return this.started;
    }
}
