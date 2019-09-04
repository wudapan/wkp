package com.bw.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class GenEntityOracle {

    private String authorName = "wanghd";// 浣滆�呭悕瀛�
    private String tablename = "eb_brand";// 琛ㄥ悕
    private String packageOutPath = "main.java.com.bw.entity";// 鎸囧畾瀹炰綋鐢熸垚鎵�鍦ㄥ寘鐨勮矾寰�
    private String[] colnames; // 鍒楀悕鏁扮粍
    private String[] colTypes; // 鍒楀悕绫诲瀷鏁扮粍
    private int[] colLens; // 瀛楁闀垮害鏁扮粍
    private String[] colComments;  //澶囨敞鏁扮粍
    private String[] tablecolnames;
    private int[] colSizes; // 鍒楀悕澶у皬鏁扮粍
    private boolean f_util = false; // 鏄惁闇�瑕佸鍏ュ寘java.util.*
    private boolean f_sql = false; // 鏄惁闇�瑕佸鍏ュ寘java.sql.*
    private String javaName = "";

    private String databaseName = "";//mysql鏁版嵁搴撳悕

    // 鏁版嵁搴撹繛鎺�
//    private static final String URL = "jdbc:oracle:thin:@192.168.215.128:1521:orcl";
//    private static final String NAME = "scott";
//    private static final String PASS = "tiger";
    private static final String URL="jdbc:mysql://localhost:3306/yuekao?user=root&password=root&useUnicode=true&characterEncoding=UTF8";
//  private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    

    public GenEntityOracle() {
        this("pingtai_kaohezhouqi","","");
    }
    
    public GenEntityOracle(String tablename,String javaName,String databaseName) {
    	this.javaName = javaName;
        this.tablename = tablename;
        this.databaseName = databaseName;
       
        //鍒ゆ柇濡傛灉鏈変笅鍒掔嚎灏辨埅鍙栵紝鍚﹀垯涓嶆埅鍙�
        if(tablename.indexOf("_")>0){
        	javaName = tablename.split("_")[1];
        }else{
        	javaName = tablename;
        }
        
    }
    
    public void init() {
        // 鍒涘缓杩炴帴
        Connection con;
        // 鏌ヨ鐢熸垚瀹炰綋绫荤殑琛�
        String sql = "select * from " + tablename;
        Statement pStemt = null;
        try {
            try {
                Class.forName(DRIVER);
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
            //鑾峰彇娉ㄩ噴
            //mysql杩炴帴闇�瑕侀渶鏀圭殑鍦版柟 		鏂板鍙傛暟databaseName 	mysql鏌ヨ娉ㄩ噴鍜宱racle涓嶅悓
            String comSql = "select column_comment from information_schema.COLUMNS where TABLE_SCHEMA='"+databaseName+"' and  table_name='"+tablename.toUpperCase()+"'";
            // String comSql = "select * from user_col_comments where  table_name='"+tablename.toUpperCase()+"'";
//            Connection ccon = DriverManager.getConnection(URL, NAME, PASS);
            Connection ccon = DriverManager.getConnection(URL);
            Statement stmt = ccon.createStatement();
            ResultSet cRs = stmt.executeQuery(comSql);
            
//            con = DriverManager.getConnection(URL, NAME, PASS);
            con = DriverManager.getConnection(URL);
            pStemt = (Statement) con.createStatement();
            ResultSet rs = pStemt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int size = rsmd.getColumnCount(); // 缁熻鍒�
            colnames = new String[size];
            colTypes = new String[size];
            colLens = new int[size];
            colComments = new String[size];
            colSizes = new int[size];
            int j = 0;
            while(cRs.next()) {
            	//MySQL闇�瑕佹敼鐨勫湴鏂�  搴斾负鎴憇elect column_comment   鎵�浠ユ煡璇㈠氨涓�涓垪  杩欒竟1 3琛ㄧず娉ㄩ噴鎵�鍦ㄥ垪鐨勪綅缃�
                colComments[j] = cRs.getString(1);
//                colComments[j] = cRs.getString(3);
                j++;
            }
            for (int i = 0; i < size; i++) {
                colnames[i] = rsmd.getColumnName(i + 1);
                colTypes[i] = rsmd.getColumnTypeName(i + 1);
                colLens[i] = rsmd.getColumnDisplaySize(i + 1);
                
                if (colTypes[i].equalsIgnoreCase("date")
                        || colTypes[i].equalsIgnoreCase("timestamp")) {
                    f_util = true;
                }
                if (colTypes[i].equalsIgnoreCase("blob")
                        || colTypes[i].equalsIgnoreCase("char")) {
                    f_sql = true;
                }
                colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
            }
            tablecolnames = colnames.clone();
            for(int i=0;i<colnames.length;i++) {
                String s = colnames[i];
                String[] strs = s.split("_");
                colnames[i] = "";
                for(int k=0;k<strs.length;k++) {
                	//鍒ゆ柇  绗竴涓厓绱犻粯璁ゅ叏灏忓啓
                    if(k == 0){
                    	colnames[i] += strs[k].toLowerCase();
                    }else{
                    	//瀹炵幇浠庣浜屽崟璇嶅紑濮嬮瀛楁瘝澶у啓
                    	String num = strs[k].toLowerCase();
                    	colnames[i] += num.substring(0,1).toUpperCase()+num.substring(1);
                    }
                }
            }
            
            String content = parse(colnames, colTypes, colSizes);

            try {
                File directory = new File("");
                // System.out.println("缁濆璺緞锛�"+directory.getAbsolutePath());
                // System.out.println("鐩稿璺緞锛�"+directory.getCanonicalPath());
                String path = this.getClass().getResource("").getPath();

                System.out.println(path);
                // System.out.println("src/?/"+path.substring(path.lastIndexOf("/com/",
                // path.length())) );
                // String outputPath = directory.getAbsolutePath()+
                // "/src/"+path.substring(path.lastIndexOf("/com/",
                // path.length()), path.length()) + initcap(tablename) +
                // ".java";
                //杩欒竟鐩綍   闇�瑕佸彉鍔ㄧ殑鏃跺�欒涓�涓�
                String outputPath = directory.getAbsolutePath()
                        + "/src/"
                        + this.packageOutPath.replace(".", "/") + "/"
                        + initcap(javaName) + ".java";
                FileWriter fw = new FileWriter(outputPath);
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // try {
            // con.close();
            // } catch (SQLException e) {
            // e.printStackTrace();
            // }
        }
    }

    /**
     * 鍔熻兘锛氱敓鎴愬疄浣撶被涓讳綋浠ｇ爜
     * 
     * @param colnames
     * @param colTypes
     * @param colSizes
     * @return
     */
    private String parse(String[] colnames, String[] colTypes, int[] colSizes) {
        StringBuffer sb = new StringBuffer();

        sb.append("package " + this.packageOutPath + ";\r\n");
        sb.append("\r\n");
        
        // 鍒ゆ柇鏄惁瀵煎叆宸ュ叿鍖�
        if (f_util) {
        }
        if (f_sql) {
            sb.append("import java.sql.*;\r\n");
        }
        
        // 娉ㄩ噴閮ㄥ垎
//        sb.append("   /**\r\n");
//        sb.append("    * " + javaName + " 瀹炰綋绫籠r\n");
//        sb.append("    * " + new Date() + " " + this.authorName + "\r\n");
//        sb.append("    */ \r\n");
        SimpleDateFormat sm = new SimpleDateFormat("yyyy骞碝M鏈坉d鏃� aHH:mm:ss");
		String time = sm.format(new Date());
		Calendar date = Calendar.getInstance();
		
        // 瀹炰綋閮ㄥ垎
        sb.append("\r\npublic class " + initcap(javaName) + " implements java.io.Serializable {\r\n");
        processAllAttrs(sb);// 灞炴��
        processAllMethod(sb);// get set鏂规硶
        sb.append("}\r\n");

        // System.out.println(sb.toString());
        return sb.toString();
    }
    /**
     * 鍔熻兘锛氱敓鎴愭墍鏈夋柟娉�
     * 
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String now = sdf.format(date);
        
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\r\n\tpublic void set" + initcap(colnames[i]) + "("
                    + sqlType2JavaType(colTypes[i]) + " " + colnames[i]
                    + "){\r\n");
            sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");
            
            sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get"
                    + initcap(colnames[i]) + "(){\r\n");
            sb.append("\t\treturn " + colnames[i] + ";\r\n");
            sb.append("\t}\r\n");
            
        }
        
        
    }
    /**
     * 鍔熻兘锛氱敓鎴愭墍鏈夊睘鎬�
     * 
     * @param sb
     */
    private void processAllAttrs(StringBuffer sb) {
        sb.append("\r\n\t//columns START\r\n");
        String str = "";
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tprivate " + sqlType2JavaType(colTypes[i]) + " "
                    + colnames[i] + ";\r\n");
            str+=","+sqlType2JavaType(colTypes[i])+" "+colnames[i];
        }
        str = str.substring(1);
        
        sb.append("\t//columns END\r\n");

        sb.append("\tpublic "+javaName+"(){\r\n");
        sb.append("\r\t}\r\n\r");
       
        sb.append("\tpublic "+javaName+"("+str+"){\r\n");
        for (int i = 0; i < colnames.length; i++) {
	        if(i!=colnames.length-1){
	        	sb.append("\t\tthis."+colnames[i]+"="+colnames[i]+";\r");
	        }else{
	        	sb.append("\t\tthis."+colnames[i]+"="+colnames[i]+";");
	        }
        }
        sb.append("\r\t}\r\n");
        
        
    }


    /**
     * 鍔熻兘锛氬皢杈撳叆瀛楃涓茬殑棣栧瓧姣嶆敼鎴愬ぇ鍐�
     * 
     * @param str
     * @return
     */
    private String initcap(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }

        return new String(ch);
    }

    /**
     * 鍔熻兘锛氳幏寰楀垪鐨勬暟鎹被鍨�
     * 
     * @param sqlType
     * @return
     */
    private String sqlType2JavaType(String sqlType) {

        if (sqlType.equalsIgnoreCase("binary_double")) {
            return "double";
        } else if (sqlType.equalsIgnoreCase("binary_float")) {
            return "float";
        } else if (sqlType.equalsIgnoreCase("char")
                || sqlType.equalsIgnoreCase("nvarchar2")
                || sqlType.equalsIgnoreCase("varchar2")
                || sqlType.equalsIgnoreCase("varchar")
                || sqlType.equalsIgnoreCase("blob")
                || sqlType.equalsIgnoreCase("clob")) {
            return "String";
        } else if (sqlType.equalsIgnoreCase("date")
                || sqlType.equalsIgnoreCase("timestamp")
                || sqlType.equalsIgnoreCase("timestamp with local time zone")
                || sqlType.equalsIgnoreCase("timestamp with time zone")) {
//            return "Date";
        	return "String";
        } else if (sqlType.equalsIgnoreCase("number") || sqlType.equalsIgnoreCase("int")) {
            return "Integer";
        }
        return "String";
    }
    public static void main(String[] args) {  
        //閲岄潰瀛樼殑鏄〃鍚�  鐢熸垚鐨勫疄浣撻瀛楁瘝澶у啓   濡備笅鏄疷ser
    	//绗竴涓弬鏁拌〃鏄�    绗簩涓弬鏁板疄浣撶被鍚�    绗笁涓弬鏁版暟鎹簱鍚嶅瓧
        new GenEntityOracle("dept","Dept","yuekao").init();  
    }  
}