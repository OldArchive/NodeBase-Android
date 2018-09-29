package global.store;

import global.NodeBaseRate;

/**
 * Created by akshaynexus on 3/3/18.
 */

public interface RateDbDao<T> extends AbstractDbDao<T>{

    NodeBaseRate getRate(String coin);


    void insertOrUpdateIfExist(NodeBaseRate nodebaseRate);

}
