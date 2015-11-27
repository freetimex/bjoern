package server;

public class Constants
{

	public static final String DEFAULT_DB_NAME = "bjoernDB";
	public static final String PLOCAL_REL_PATH_TO_DBS = "plocal:../databases/";
	public static final String PLOCAL_PATH_TO_DB = PLOCAL_REL_PATH_TO_DBS
			+ DEFAULT_DB_NAME;
	public static final String QUERY_LIB_DIR = "../../querylib/";
	public static final String DB_USERNAME = "root";
	public static final String DB_PASSWORD = "admin";

	public static final String INDEX_NAME = "[addr,childNum,code,comment,key,nodeType,repr]";
}