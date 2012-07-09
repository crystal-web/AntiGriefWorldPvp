package fr.iyc.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class MySQL extends Database
{
  private String hostname = "localhost";
  private String portnmbr = "3306";
  private String username = "minecraft";
  private String password = "";
  private String database = "minecraft";

  public MySQL(Logger log, String prefix, String hostname, String portnmbr, String database, String username, String password)
  {
    super(log, prefix, "[MySQL] ");
    this.hostname = hostname;
    this.portnmbr = portnmbr;
    this.database = database;
    this.username = username;
    this.password = password;
  }

  protected boolean initialize()
  {
    try {
      Class.forName("com.mysql.jdbc.Driver");
      return true;
    } catch (ClassNotFoundException e) {
      writeError("Class Not Found Exception: " + e.getMessage() + ".", true);
    }return false;
  }

  public Connection open()
  {
    if (initialize()) {
      String url = "";
      
      url = "jdbc:mysql://" + this.hostname + ":" + this.portnmbr + "/" + this.database;
      try {
        

        this.connection = DriverManager.getConnection(url, this.username, this.password);
      } catch (SQLException e) {
        writeError(url, true);
        writeError("Could not be resolved because of an SQL Exception: " + url + " " + e.getMessage() + ".", true);
      }
    }
    return null;
  }

  public void close()
  {
    try
    {
      if (this.connection != null)
        this.connection.close();
    } catch (Exception e) {
      writeError("Failed to close database connection: " + e.getMessage(), true);
    }
  }

  public Connection getConnection()
  {
    return this.connection;
  }

  public boolean checkConnection()
  {
    return this.connection != null;
  }

  @Override
  public ResultSet query(String query) {
      //Connection connection = null;
      Statement statement = null;
      ResultSet result = null/*new JdbcRowSetImpl()*/;
      try {
          //connection = open();
          //if (checkConnection())
          statement = this.connection.createStatement();
          result = statement.executeQuery("SELECT CURTIME()");
       
          switch (this.getStatement(query)) {
              case SELECT:
                  result = statement.executeQuery(query);
                  break;
           
              default:
                  statement.executeUpdate(query);
          }
          //connection.close();
          return result;
      } catch (SQLException e) {
          this.writeError("Error in SQL query: " + e.getMessage(), false);
      }
      return result;
  }
  

  public PreparedStatement prepare(String query)
  {
    PreparedStatement ps = null;
    try
    {
      ps = this.connection.prepareStatement(query);
      return ps;
    } catch (SQLException e) {
      if (!e.toString().contains("not return ResultSet"))
        writeError("Error in SQL prepare() query: " + e.getMessage(), false);
    }
    return ps;
  }

  public boolean createTable(String query)
  {
    Statement statement = null;
    try
    {
      if ((query.equals("")) || (query == null)) {
        writeError("SQL query empty: createTable(" + query + ")", true);
        return false;
      }

      statement = this.connection.createStatement();
      statement.execute(query);
      return true;
    } catch (SQLException e) {
      writeError(e.getMessage(), true);
      return false;
    } catch (Exception e) {
      writeError(e.getMessage(), true);
    }return false;
  }

  public boolean checkTable(String table)
  {
    try
    {
      Statement statement = this.connection.createStatement();

      ResultSet result = statement.executeQuery("SELECT * FROM " + table);

      if (result == null)
        return false;
      if (result != null)
        return true;
    } catch (SQLException e) {
      if (e.getMessage().contains("exist")) {
        return false;
      }
      writeError("Error in SQL query: " + e.getMessage(), false);

      if (query("SELECT * FROM " + table) == null) return true; 
    }
    return false;
  }

  public boolean wipeTable(String table)
  {
    Statement statement = null;
    String query = null;
    try {
      if (!checkTable(table)) {
        writeError("Error wiping table: \"" + table + "\" does not exist.", true);
        return false;
      }

      statement = this.connection.createStatement();
      query = "DELETE FROM " + table + ";";
      statement.executeUpdate(query);

      return true;
    } catch (SQLException e) {
      if (!e.toString().contains("not return ResultSet"))
        return false;
    }
    return false;
  }
}