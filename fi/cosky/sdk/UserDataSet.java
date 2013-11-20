package fi.cosky.sdk;
import java.util.ArrayList;

/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

public class UserDataSet extends BaseData {
    private ArrayList<UserData> Items;
    private int VersionNumber;
    private int ClientId;
    
    public ArrayList<UserData> getItems() {
        return Items;
    }

    public void setItems(ArrayList<UserData> items) {
        Items = items;
    }

	int getVersionNumber() {
		return VersionNumber;
	}

	void setVersionNumber(int versionNumber) {
		VersionNumber = versionNumber;
	}
    
}
