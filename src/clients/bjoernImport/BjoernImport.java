package clients.bjoernImport;

import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.cli.ParseException;

import exporters.radare.RadareExporter;

public class BjoernImport
{

	static CommandLineInterface cmdLine = new CommandLineInterface();

	public static void main(String[] args) throws IOException
	{
		parseCommandLine(args);
		invokeRadare2(args);
		invokeImportPlugin();
	}

	private static void invokeRadare2(String[] args) throws IOException
	{
		String pathToBinary = cmdLine.getCodedir();
		RadareExporter.export(pathToBinary, ".");
	}

	private static void invokeImportPlugin() throws MalformedURLException
	{

		String workingDirectory = System.getProperty("user.dir");

		Path nodePath = Paths.get(workingDirectory, "nodes.csv");
		String nodeFilename = URLEncoder.encode(nodePath.toAbsolutePath()
				.toString());

		Path edgePath = Paths.get(workingDirectory, "edges.csv");
		String edgeFilename = URLEncoder.encode(edgePath.toAbsolutePath()
				.toString());

		String dbName = URLEncoder.encode(cmdLine.getDbName());

		Path keyedEdgePath = Paths.get(workingDirectory, "keyedEdges.csv");
		String keyedEdgeFilename = URLEncoder.encode(keyedEdgePath
				.toAbsolutePath().toString());

		String urlStr = String.format(
				"http://localhost:2480/importcsv/%s/%s/%s/%s/", nodeFilename,
				edgeFilename, dbName, keyedEdgeFilename);

		URL url = new URL(urlStr);
		HttpURLConnection connection;

		try
		{
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(0);
			connection.getInputStream();
		}
		catch (ConnectException ex)
		{
			System.out
					.println("You need to start the bjoern-server (bjoern-server.sh).");
		}
		catch (IOException ex)
		{
			if (ex.getMessage().contains("405"))
				System.err
						.println("Cannot invoke server plugin."
								+ " Try copying `conf/orientdb-server-config.xml "
								+ "to orientdb-2.1.5-community/config/ and restart the server.`");
		}

	}

	private static void parseCommandLine(String[] args)
	{
		try
		{
			cmdLine.parseCommandLine(args);
		}
		catch (RuntimeException | ParseException e)
		{
			printHelpAndTerminate(e);
		}
	}

	private static void printHelpAndTerminate(Exception e)
	{
		System.err.println(e.getMessage());
		cmdLine.printHelp();
		System.exit(0);
	}

}