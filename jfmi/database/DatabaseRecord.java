package jfmi.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DatabaseRecord {

	public String getUniqueColumnName();
	public String getPSCheckExistsSQL();
	public void setPSCheckExists(PreparedStatement checkExists) throws 
		SQLException;

	public String toString();
}
