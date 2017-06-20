package mysql;


import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MysqlUtil {

    // 链接
    private Connection conn = null;
    // 返回集合
    private ResultSet rs = null;
    //
    private Statement sm = null;

    // URL
    private String insideUrl = "";
    // MYSQL用户名
    private String insideUsername = "";
    // MYSQL密码
    private String insidePassw = "";

    public MysqlUtil(String url, String username, String password) {
        this.insideUrl = url;
        this.insideUsername = username;
        this.insidePassw = password;
    }

    /**
     * Connection
     */
    public void mysqlConn() {

        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                conn = DriverManager.getConnection(insideUrl + "&user=" + insideUsername + "&password=" + insidePassw);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ResultSet To ArrayList
     */
    public List<HashMap<String, Object>> resultSetMetaDataToArrayList(ResultSet rs) throws SQLException {
        List<HashMap<String, Object>> list = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        while (rs.next()) {
            HashMap<String, Object> ht = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                ht.put(rsmd.getColumnName(i).toLowerCase(), rs.getString(rsmd.getColumnName(i)));
            }
            list.add(ht);
        }
        return list;
    }

    /**
     * SELECT
     */
    public List<HashMap<String, Object>> selectSql(String sql) {
        mysqlConn();
        try {
            if (sm == null)
                sm = conn.createStatement();
            rs = sm.executeQuery(sql);
            return resultSetMetaDataToArrayList(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * INSERT DELETE UPDATE
     */
    public int insertSql(String sql) {
        try {
            mysqlConn();
            if (sm == null) {
                sm = conn.createStatement();
            }
            return sm.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 关闭链接
     */
    public void close() {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            rs = null;
        }
        if (sm != null) {
            try {
                sm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            sm = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conn = null;
        }
    }

}
