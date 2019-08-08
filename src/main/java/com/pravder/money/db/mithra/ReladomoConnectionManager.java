package com.pravder.money.db.mithra;

import com.gs.fw.common.mithra.bulkloader.BulkLoader;
import com.gs.fw.common.mithra.bulkloader.BulkLoaderException;
import com.gs.fw.common.mithra.connectionmanager.SourcelessConnectionManager;
import com.gs.fw.common.mithra.connectionmanager.XAConnectionManager;
import com.gs.fw.common.mithra.databasetype.DatabaseType;
import com.gs.fw.common.mithra.databasetype.H2DatabaseType;
import org.h2.tools.RunScript;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Stream;

public class ReladomoConnectionManager implements SourcelessConnectionManager {

  private static ReladomoConnectionManager instance;
  private XAConnectionManager xaConnectionManager;

  public static synchronized ReladomoConnectionManager getInstance() {
    if (instance == null) {
      instance = new ReladomoConnectionManager();
    }
    return instance;
  }

  private ReladomoConnectionManager() {
    this.createConnectionManager();
    try {
      this.createTables();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  private XAConnectionManager createConnectionManager() {
    xaConnectionManager = new XAConnectionManager();
    xaConnectionManager.setDriverClassName("org.h2.Driver");
    xaConnectionManager.setJdbcConnectionString("jdbc:h2:mem:myDb");
    xaConnectionManager.setJdbcUser("sa");
    xaConnectionManager.setJdbcPassword("");
    xaConnectionManager.setPoolName("My Connection Pool");
    xaConnectionManager.setInitialSize(1);
    xaConnectionManager.setPoolSize(10);
    xaConnectionManager.initialisePool();
    return xaConnectionManager;
  }

  public void createTables() throws Exception {
        Connection conn = xaConnectionManager.getConnection();
        createDBTable(conn, getAccountCreateQuery());
        createDBTable(conn, getBalanceCreateQuery());
        createDBTable(conn, getTransactionCreateQuery());
  }


//  public void createTables() throws Exception {
//    URI uri = ClassLoader.getSystemResource("sql").toURI();
//    Path ddlPath = Paths.get(uri);
//    initFileSystem(uri);
//    try (
//        Connection conn = xaConnectionManager.getConnection();
//        Stream<Path> list = Files.list(ddlPath)) {
//      list.forEach(path -> {
//        System.out.println(path.toString());
//
//        if (path.toString().endsWith("ddl")) {
//          try {
//            RunScript.execute(conn, Files.newBufferedReader(path));
//          } catch (SQLException | IOException exc) {
//            exc.printStackTrace();
//          }
//        }
//      });
//    }
//  }

  //Create new H2 Database table
  private void createDBTable(Connection connection,
                             String createQuery)
  {

    try
    {
      connection.setAutoCommit(false);
      Statement statement = connection.createStatement();
      statement.execute(createQuery);
      statement.close();
      connection.commit();
    }
    catch(Exception ex)
    {
      System.out.println(ex.toString());
    }
  }

  private String getAccountCreateQuery()
  {
    String createSQLQuery = "create table ACCOUNT" +
        "(" +
        "    id char(128) not null," +
        "    name varchar(255) not null," +
        "    email varchar(255) not null," +
        "    address varchar(255) not null," +
        "    base_ccy varchar(255) not null" +
        ");";

    return  createSQLQuery;

  }

  private String getBalanceCreateQuery()
  {
    String createSQLQuery = "create table BALANCE" +
        "(" +
        "    customer_id char(128) not null," +
        "    currency varchar(255) not null," +
        "    business_date timestamp not null," +
        "    balance numeric(20,5)" +
        ");";

    return  createSQLQuery;
  }

  private String getTransactionCreateQuery()
  {
    String createSQLQuery = "create table TRANSACTION" +
        "(" +
        "    id char(128) not null," +
        "    customerId int not null," +
        "    debitCreditFlag varchar(1) not null," +
        "    amount numeric(20,5) not null," +
        "    currency varchar(255) not null," +
        "    businessDate timestamp," +
        "    counterParty varchar(255)," +
        "    source varchar(255)," +
        "    comment varchar(255)" +
        ");";

    return  createSQLQuery;

  }



  private void initFileSystem(URI uri) throws IOException {
    try {
      FileSystems.getFileSystem(uri);
    } catch (FileSystemNotFoundException e) {
      Map<String, String> env = new HashMap<>();
      env.put("create", "true");
      FileSystems.newFileSystem(uri, env);
    }
  }

  @Override
  public BulkLoader createBulkLoader() throws BulkLoaderException {
    return null;
  }

  @Override
  public Connection getConnection() {
    return xaConnectionManager.getConnection();
  }

  @Override
  public DatabaseType getDatabaseType() {
    return H2DatabaseType.getInstance();
  }

  @Override
  public TimeZone getDatabaseTimeZone() {
    return TimeZone.getDefault();
  }

  @Override
  public String getDatabaseIdentifier() {
    return "transactionDB";
  }
}
