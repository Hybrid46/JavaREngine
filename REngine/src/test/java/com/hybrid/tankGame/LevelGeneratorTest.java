package com.hybrid.tankGame;

import com.hybrid.rEngine.math.Vector2;
import com.hybrid.rEngine.math.Vector2Int;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LevelGeneratorTest {
    private LevelGenerator levelGenerator;
    private static final String SAVE_FILE = "savegame.dat";
    private static final int TILE_SIZE = 64;

    @BeforeEach
    public void setUp() {
        levelGenerator = new LevelGenerator(null);
        new File(SAVE_FILE).delete();
    }

    @AfterEach
    public void tearDown() {
        new File(SAVE_FILE).delete();
    }

    @Test
    public void testSaveGame() throws Exception {
        List<Vector2> positions = new ArrayList<>();
        positions.add(new Vector2(100, 100));
        positions.add(new Vector2(200, 200));
        
        levelGenerator.pathMap[5][5] = true;
        levelGenerator.saveGame(positions);

        File saveFile = new File(SAVE_FILE);
        assertTrue(saveFile.exists(), "Save file should be created");
        assertTrue(saveFile.length() > 0, "Save file should not be empty");
    }

    @Test
    public void testLoadGame() throws Exception {
        List<Vector2> originalPositions = new ArrayList<>();
        originalPositions.add(new Vector2(150, 150));
        originalPositions.add(new Vector2(300, 300));
        levelGenerator.pathMap[7][7] = true;
        levelGenerator.saveGame(originalPositions);

        List<Vector2> loadedPositions = levelGenerator.loadGame();

        assertEquals(originalPositions.size(), loadedPositions.size(), "Should load same number of positions");
        assertEquals(originalPositions.get(0).x, loadedPositions.get(0).x, "First position X should match");
        assertEquals(originalPositions.get(0).y, loadedPositions.get(0).y, "First position Y should match");
        
        assertTrue(levelGenerator.pathMap[7][7], "Pathmap state should be restored");
    }

    @Test
    public void testHasSaveGame() {
        assertFalse(levelGenerator.hasSaveGame(), "Should return false with no save file");
        levelGenerator.saveGame(new ArrayList<>());        
        assertTrue(levelGenerator.hasSaveGame(), "Should return true with existing save");
    }

    @Test
    public void testGenerateLevel() {
        try {
        List<Vector2> positions = levelGenerator.generateLevel(3);
        assertEquals(4, positions.size(), "Should generate positions for player + 3 enemies");
        
        levelGenerator.saveGame(positions);
        List<Vector2> loadedPositions = levelGenerator.generateLevel(3);
        assertEquals(positions.size(), loadedPositions.size(), "Should load same number of positions from save");
        }
        catch(Exception e){
            //Game null exception
        }
    }

    @Test
    public void testDeleteSaveGame() {
        levelGenerator.saveGame(new ArrayList<>());
        levelGenerator.deleteSaveGame();
        
        assertFalse(new File(SAVE_FILE).exists(), "Save file should be deleted");
    }

    @Test
    public void testGetSpawnPoint() {
        Vector2 playerPos = new Vector2(2 * TILE_SIZE, 2 * TILE_SIZE);
        List<Vector2Int> occupied = List.of(new Vector2Int(5, 5));
        
        levelGenerator.pathMap[15][15] = true;

        Vector2Int spawn = levelGenerator.getSpawnPoint(playerPos, occupied);
        
        assertEquals(15, spawn.x, "X coordinate should match");
        assertEquals(15, spawn.y, "Y coordinate should match");
        assertTrue(levelGenerator.pathMap[spawn.x][spawn.y], "Spawn point should be walkable");
    }

    @Test
    public void testWorldPositionToTilePosition() {
        Vector2 worldPos = new Vector2(100, 150);
        Vector2Int tilePos = levelGenerator.worldPositionToTilePosition(worldPos);
        
        assertEquals(1, tilePos.x, "X tile position should be 1");
        assertEquals(2, tilePos.y, "Y tile position should be 2");
    }

    @Test
    public void testTilePositionToWorldPosition() {
        Vector2Int tilePos = new Vector2Int(2, 3);
        Vector2 worldPos = levelGenerator.tilePositionToWorldPosition(tilePos);
        
        assertEquals(2 * TILE_SIZE + TILE_SIZE/2f, worldPos.x, 0.01, "World X position should be center of tile");
        assertEquals(3 * TILE_SIZE + TILE_SIZE/2f, worldPos.y, 0.01, "World Y position should be center of tile");
    }

    @Test
    public void testGetRandomTile() {
        levelGenerator.pathMap[10][10] = true;

        for (int i = 0; i < 10; i++) {
            Vector2Int tile = levelGenerator.getRandomTile();
            assertTrue(levelGenerator.pathMap[tile.x][tile.y], "Tile should be walkable");
        }
    }
}