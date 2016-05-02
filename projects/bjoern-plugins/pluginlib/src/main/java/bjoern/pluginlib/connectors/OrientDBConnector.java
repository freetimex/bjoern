package bjoern.pluginlib.connectors;

import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

import octopus.server.Constants;

public class OrientDBConnector {

	private static final int MAX_POOL_SIZE = 10;
	private OrientGraphFactory graphFactory;

	public void connect(String databaseName)
	{
		graphFactory = new OrientGraphFactory(
				Constants.PLOCAL_REL_PATH_TO_DBS + databaseName)
				.setupPool(1, MAX_POOL_SIZE);
	}

	public OrientGraphNoTx getNoTxGraphInstance()
	{
		return graphFactory.getNoTx();
	}

	public OrientGraph getGraphInstance()
	{
		return graphFactory.getTx();
	}

	public void disconnect()
	{
		graphFactory.close();
	}

}