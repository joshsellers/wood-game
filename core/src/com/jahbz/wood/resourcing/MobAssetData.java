package com.jahbz.wood.resourcing;

public class MobAssetData {
    public final static MobAssetData[] DATA = new MobAssetData[256];

    public static final MobAssetData TEST_MOB = new MobAssetData(0x00, 8, 25, 1, 2,
            0, 27, 8, 0.075f);

    private final int xTile, yTile;
    private final int width, height;

    private final int movingAnimationXTile, movingAnimationYTile, movingAnimationLength;
    private final float frameInterval;

    private final int id;

    private AssetCreator.MobAssetCapsule assetCapsule = null;
    private boolean hasCapsule = false;

    public MobAssetData(int id, int xTile, int yTile, int width, int height, int movingAnimationXTile,
                        int movingAnimationYTile, int movingAnimationLength, float frameInterval) {
        this.id = id;
        if (DATA[id] != null)
            throw new RuntimeException("Duplicate mob data id at " + id);
        DATA[id] = this;

        this.xTile = xTile;
        this.yTile = yTile;
        this.width = width;
        this.height = height;
        this.movingAnimationXTile = movingAnimationXTile;
        this.movingAnimationYTile = movingAnimationYTile;
        this.movingAnimationLength = movingAnimationLength;
        this.frameInterval = frameInterval;
    }

    public void setAssetCapsule(AssetCreator.MobAssetCapsule capsule) {
        assetCapsule = capsule;
        hasCapsule = true;
    }

    public AssetCreator.MobAssetCapsule getAssetCapsule() {
        if (hasCapsule()) return assetCapsule;
        else throw new RuntimeException("An asset capsule has not been created for mob " + getId());
    }

    public static MobAssetData getData(int id) {
        return DATA[id];
    }

    public static AssetCreator.MobAssetCapsule getAssetCapsule(int id) {
        return getData(id).getAssetCapsule();
    }

    public int getXTile() {
        return xTile;
    }

    public int getYTile() {
        return yTile;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getMovingAnimationXTile() {
        return movingAnimationXTile;
    }

    public int getMovingAnimationYTile() {
        return movingAnimationYTile;
    }

    public int getMovingAnimationLength() {
        return movingAnimationLength;
    }

    public float getFrameInterval() {
        return frameInterval;
    }

    public int getId() {
        return id;
    }

    public boolean hasCapsule() {
        return hasCapsule;
    }
}
