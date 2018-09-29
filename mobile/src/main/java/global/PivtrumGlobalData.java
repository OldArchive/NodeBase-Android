package global;

import org.nodebasej.core.Coin;
import org.nodebasej.core.CoinDefinition;

import java.util.ArrayList;
import java.util.List;

import pivtrum.PivtrumPeerData;

/**
 * Created by akshaynexus on 7/2/17.
 */

public class PivtrumGlobalData {

    public static final String FURSZY_TESTNET_SERVER = CoinDefinition.dnsSeeds[2];

    public static final String[] TRUSTED_NODES = CoinDefinition.dnsSeeds;

    public static final List<PivtrumPeerData> listTrustedHosts(){
        List<PivtrumPeerData> list = new ArrayList<>();
        list.add(new PivtrumPeerData(FURSZY_TESTNET_SERVER, CoinDefinition.Port,55552));
        for (String trustedNode : TRUSTED_NODES) {
            list.add(new PivtrumPeerData(trustedNode, CoinDefinition.Port,55552));
        }
        return list;
    }

}
