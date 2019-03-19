package com.github.psnrigner.mha.plugin.game.ffa;

import com.github.psnrigner.mha.plugin.game.WorldConfiguration;

import java.util.List;

class FFAWorldConfiguration extends WorldConfiguration
{
    List<Location> spawns;
    int lives;

    public static class Location
    {
        double x;
        double y;
        double z;
    }
}
