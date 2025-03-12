package com.hybrid.tankGame;

import com.hybrid.rEngine.main.Game;

import java.util.Arrays;

public class LevelGenerator {

    private final int MAP_SIZE = 20;
    public final boolean[][] pathMap = new boolean[MAP_SIZE][MAP_SIZE];
    private final int TILE_SIZE = 64;

    public void generateLevel(Game game) {
        generateRandomMap();
        //filter unreachable areas -> make sure all neighbours are walkable
        filterNeighbours();
        printMap();
        generateTileEntities(game);
    }

    private void generateRandomMap() {
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                if (x == 0 || y == 0 || x == MAP_SIZE - 1 || y == MAP_SIZE - 1) {
                    pathMap[x][y] = false;
                } else {
                    pathMap[x][y] = Math.random() > 0.5d;
                }
            }
        }
    }

    private void filterNeighbours() {
        for (int y = 2; y < MAP_SIZE - 2; y++) {
            for (int x = 2; x < MAP_SIZE - 2; x++) {
                if (!pathMap[x][y]) {

                    for (int yy = -1; yy <= 1; yy++) {
                        for (int xx = -1; xx <= 1; xx++) {
                            if (xx == 0 && yy == 0) continue;

                            int nx = x + xx;
                            int ny = y + yy;

                            //skip out of map bounds
                            if (nx < 0 || nx >= MAP_SIZE) continue;
                            if (ny < 0 || ny >= MAP_SIZE) continue;

                            pathMap[nx][ny] = true;
                        }
                    }
                }
            }
        }
    }

    private void generateTileEntities(Game game) {
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                new Tile(game, x * TILE_SIZE, y * TILE_SIZE, pathMap[x][y]);
            }
        }
    }

    private void printMap() {
        System.out.println("[LevelGenerator] Path Map:");
        System.out.println(Arrays.deepToString(pathMap).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
    }
}
