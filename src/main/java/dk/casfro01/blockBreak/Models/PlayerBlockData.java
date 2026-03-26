package dk.casfro01.blockBreak.Models;

public class PlayerBlockData {
    private final String uuid;
    private int blocks;
    private boolean dirty = false;

    public PlayerBlockData(String uuid, int blocks) {
        this.uuid = uuid;
        this.blocks = blocks;
    }

    public String getUuid() {
        return uuid;
    }

    public int getBlocks() {
        return blocks;
    }

    public void incrementBlocks(){
        if (!dirty) dirty = true;
        blocks++;
    }

    public boolean isDirty(){
        return dirty;
    }
}
