package com.iiaccount.dao;

import com.iiaccount.utils.GetFileMess;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

//执行sql文件语句
public class ExcutSqlFile {
    
    public static void excute(String fileName) throws SQLException, IOException, ClassNotFoundException {
        Connection conn = DBDPConnection.getDPConnection();
        ScriptRunner runner = new ScriptRunner(conn);
        Resources.setCharset(Charset.forName("UTF-8"));
        runner.setDelimiter(";"); //语句结束符号设置
        runner.setLogWriter(null);//设置是否输出日志

        //案例执行前的参数维护
        Reader reader = Resources.getResourceAsReader("./sql/"+ fileName +".sql");
        runner.runScript(reader);
        runner.closeConnection();
        conn.close();
    }
}
