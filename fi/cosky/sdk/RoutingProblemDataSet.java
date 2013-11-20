package fi.cosky.sdk;
import java.util.List;

/*
 * This file is subject to the terms and conditions defined in
 * file 'LICENSE.txt', which is part of this source code package.
 */

public class RoutingProblemDataSet extends BaseData {
    private List<RoutingProblemData> Items;
    private int VersionNumber;

    int getVersionNumber() {
		return VersionNumber;
	}

	void setVersionNumber(int versionNumber) {
		VersionNumber = versionNumber;
	}

	public List<RoutingProblemData> getItems() {
        return Items;
    }

    public void setItems(List<RoutingProblemData> items) {
        Items = items;
    }
}
