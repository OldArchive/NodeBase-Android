package nodebase.tech.akshaynexus;

/*
 * Created by sh_zam on 3/10/18
 */

public class JsonData {

	private String mPubKey;
	private String mStatus;
	private String mPayeeAddress;

	public JsonData(String pubKey, String status, String payeeAddress) {
		mPubKey = pubKey;
		mStatus = status;
		mPayeeAddress = payeeAddress;
	}

	public String getPubKey() {
		return mPubKey;
	}

	public String getStatus() {
		return mStatus;
	}

	public String getPayeeAddress() {
		return mPayeeAddress;
	}
}
