package dk.casfro01.blockBreak.Models;

public class TopPlayerBlockData extends PlayerBlockData {

    private int place = 0;
    public TopPlayerBlockData(String uuid, int blocks, int place) {
        super(uuid, blocks);
        this.place = place;
    }

    public int getPlace(){
        return place;
    }
}
