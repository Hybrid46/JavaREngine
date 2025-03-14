package com.hybrid.tankGame;

import com.hybrid.rEngine.main.Game;
import com.hybrid.rEngine.math.Vector2;

import java.io.*;
import java.util.Arrays;

public class LevelGenerator {

    private final int MAP_SIZE = 20;
    public final boolean[][] pathMap = new boolean[MAP_SIZE][MAP_SIZE];
    private final int TILE_SIZE = 64;
    private final String SAVE_FILE_NAME = "savegame.dat";
    private Game game;

    public LevelGenerator(Game game) {
        this.game = game;
    }

    public void saveGame(Vector2[] playerPositions) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE_NAME))) {
            out.writeObject(playerPositions);
            out.writeObject(pathMap);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Vector2[] loadGame() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE_NAME))) {
            Vector2[] positions = (Vector2[]) in.readObject();
            boolean[][] savedPathMap = (boolean[][]) in.readObject();

            // Copy the loaded pathMap into the current pathMap
            for (int i = 0; i < MAP_SIZE; i++) {
                System.arraycopy(savedPathMap[i], 0, this.pathMap[i], 0, MAP_SIZE);
            }

            return positions;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean hasSaveGame() {
        File saveFile = new File(SAVE_FILE_NAME);
        if (!saveFile.exists()) {
            System.out.println("Save file does not exist. Generating a new level...");
            return false;
        } else {
            System.out.println("Loading Game from save file...");
            return true;
        }
    }

    public Vector2[] generateLevel() {
        Vector2[] positions;

        if (hasSaveGame()) {
            positions = loadGame();
        } else {
            generateRandomMap();
            filterNeighbours();
            positions = new Vector2[]{ new Vector2(100,100)};
        }
        printMap();
        generateTileEntities();

        return positions;
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

    //filter unreachable areas -> make sure all neighbours are walkable
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

    private void generateTileEntities() {
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

    public void deleteSaveGame() {
        File saveFile = new File(SAVE_FILE_NAME);
        if (saveFile.exists()) {
            saveFile.delete();
        }
    }
}
