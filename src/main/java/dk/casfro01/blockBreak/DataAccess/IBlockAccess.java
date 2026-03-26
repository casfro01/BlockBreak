package dk.casfro01.blockBreak.DataAccess;

import java.util.List;

public interface IBlockAccess<T, ID> {
    T get(ID param) throws Exception;
    List<T> getAll(List<ID> param) throws Exception;

    boolean saveAll(List<T> param) throws Exception;
    boolean save(T param) throws Exception;

}
