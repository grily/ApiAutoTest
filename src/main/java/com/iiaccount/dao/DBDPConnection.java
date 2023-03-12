package com.iiaccount.dao;

import com.iiaccount.utils.GetFileMess;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBDPConnection {

    static Logger log = Logger.getLogger(DBDPConnection.class);

    static Connection dBConnection = null; //数据库链接 oracle
    static Connection dPConnection = null; //数据池链接 mysql

    //数据库连接，oracle
//    public static Connection getDBConnection() throws ClassNotFoundException, IOException, SQLException {
//        String database = "iiaccount_db.properties"; //通过filter配置数据库环境
//        String url = new GetFileMess().getValue("DB_IP",database);
//        String user = new GetFileMess().getValue("DB_Name",database);
//        String password = new GetFileMess().getValue("DB_Password",database);
//
//        String driverName="com.mysql.cj.jdbc.Driver";
//        String dbURL="jdbc:mysql://localhost:3306/api_autotest?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Shanghai";
//
//        try {
//            Class.forName(driverName);
//            System.out.println("加载MySQL驱动成功");
//        } catch (ClassNotFoundException e) {
//            System.out.println("加载MySQL驱动失败");
//        }
//        try (Connection dbConnection = DriverManager.getConnection(dbURL, user, password)) {
//
//            System.out.println("连接数据库成功");
//            return dbConnection;
//        } catch(SQLException e) {
//            System.out.println("数据库连接失败");
//        }
//
//
////        dBConnection = DriverManager.getConnection(url, user, password);
//        return null;
//    }

    //数据池连接，mysql
    public static Connection getDPConnection() throws IOException, ClassNotFoundException, SQLException {
        String database = "iiaccount_db.properties"; //通过filter配置数据库环境
        String user = new GetFileMess().getValue("DP_Name",database);
        String password = new GetFileMess().getValue("DP_Password",database);
//        String dbURL= new GetFileMess().getValue("DP_URL",database);//读不出来
        //&useSSL=false&serverTimezone=Asia/Shanghai
        String dbURL = "jdbc:mysql://localhost:3306/api_autotest?characterEncoding=UTF-8&autoReconnect=true";
        String driverName="com.mysql.cj.jdbc.Driver";

        // 加载驱动程序
//        Class.forName("com.mysql.jdbc.Driver");
//        url = "jdbc:mysql://" + url +"?characterEncoding=gb2312&serverTimezone=UTC";  //ip配置;
//        log.info("数据池："+url+"|"+user+"|"+password);
//        dPConnection = DriverManager.getConnection(url, user, password);
//        return dPConnection;
        try {
            Class.forName(driverName);
            System.out.println("加载MySQL驱动成功");
        } catch (ClassNotFoundException e) {
            System.out.println("加载MySQL驱动失败");
        }
        try  {
            Connection dbConnection = DriverManager.getConnection(dbURL, user, password);
            System.out.println("连接数据库成功");
            return dbConnection;
        } catch(SQLException e) {
            System.out.println("数据库连接失败");
        }


//        dBConnection = DriverManager.getConnection(url, user, password);
        return null;
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
//        DBDPConnection db = new DBDPConnection();
        getDPConnection();

    }
}
