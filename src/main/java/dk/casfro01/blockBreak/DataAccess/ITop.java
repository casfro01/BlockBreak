package dk.casfro01.blockBreak.DataAccess;

import java.util.List;

public interface ITop<T> {

    List<T> getTopTen(int page) throws Exception;
}
