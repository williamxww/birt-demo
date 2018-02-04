package com.bow.base;

import java.util.Collection;

import org.eclipse.birt.data.oda.pojo.api.PojoDataSetFromCollection;
import org.eclipse.datatools.connectivity.oda.OdaException;

public class BaseDataSet extends PojoDataSetFromCollection {

	@Override
	protected Collection fetchPojos() throws OdaException {
		return null;
	}

}
