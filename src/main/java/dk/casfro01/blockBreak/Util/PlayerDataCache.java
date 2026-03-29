package dk.casfro01.blockBreak.Util;

import dk.casfro01.blockBreak.Models.PlayerBlockData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDataCache {
    private Map<String, PlayerBlockData> cache = new HashMap<>();

    public PlayerBlockData get(String uuid){
        return cache.getOrDefault(uuid, null);
    }

    public List<PlayerBlockData> getList(){
        return new ArrayList<>(cache.values());
    }

    public void addPlayer(String uuid, PlayerBlockData data){
        cache.put(uuid, data);
    }

    public void remove(String uuid){
        cache.remove(uuid);
    }

    public void renew(List<PlayerBlockData> dataList){
        cache.clear();
        for (PlayerBlockData data : dataList){
            cache.put(data.getUuid(), data);
        }
    }
}
