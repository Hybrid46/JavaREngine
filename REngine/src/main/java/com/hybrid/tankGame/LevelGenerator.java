package com.hybrid.tankGame;

import com.hybrid.rEngine.main.Game;

public class LevelGenerator {

    private final int MAP_SIZE = 20;
    private final int TILE_SIZE = 64;

    public boolean[][] pathMap = new boolean[MAP_SIZE][MAP_SIZE];

    public void generateLevel(Game game) {
        //path map first
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                pathMap[x][y] = Math.random() > 0.5f;
            }
        }

        //filter unreachable areas -> make sure all neighbours are walkable
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                if (!pathMap[x][y]) { //non walkable -> set all neighbours walkable
                    //neighbours
                    for (int yy = -1; yy < 1; yy++) {
                        for (int xx = -1; xx < 1; xx++) {
                            //skip out of map bounds
                            if (xx < 0 || xx >= MAP_SIZE) continue;
                            if (yy < 0 || yy >= MAP_SIZE) continue;

                            if (pathMap[xx][yy] == false) pathMap[xx][yy] = true;
                        }
                    }
                }
            }
        }

        //tile Entity generation
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                new Tile(game, x * TILE_SIZE, y * TILE_SIZE, pathMap[x][y]);
            }
        }
    }
}
