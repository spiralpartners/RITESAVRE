package jp.enpit.cloud.ritesavre.mybatis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MyBatisConnectionFactory {
	private static SqlSessionFactory sqlSessionFactory;
	private static String database;
	//private static MyBatisConnectionFactory connect = new MyBatisConnectionFactory();

    public static SqlSessionFactory getSqlSessionFactory(String database) {
        try {
        	Properties props = new Properties();
        	props.setProperty("url", "jdbc:mysql://10.1.0.67:33060/" + database);
        	props.setProperty("username", "tracuser");
        	props.setProperty("password", "cspiral");
        	props.setProperty("driver", "com.mysql.jdbc.Driver");


            String resource = "jp/enpit/cloud/ritesavre/mybatis/mybatis-config.xml";
            Reader reader = Resources.getResourceAsReader(resource);

            //sqlSessionFactoryがnullもしくはdatabase引数が以前のものと変わった場合にのみ新しいsqlSessionFactoryを返す
            if (sqlSessionFactory == null || MyBatisConnectionFactory.database.equals(database) == false) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, props);
            	//sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
                MyBatisConnectionFactory.database = database;
            }
        }
        catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        return sqlSessionFactory;
    }
}
