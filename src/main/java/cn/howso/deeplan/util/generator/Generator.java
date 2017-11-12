package cn.howso.deeplan.util.generator;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.shiro.util.Assert;

/** 根据model自动生成表、自动修改mapper.xml的BaseResultMap */
public class Generator {

	public File getTargetDirectory() {
		return new File(Generator.class.getResource("/").getFile()).getParentFile();
	}

	public File getClassesDirectory() {
		File[] files = getTargetDirectory().listFiles(f -> Objects.equals(f.getName(), "classes"));
		Assert.isTrue(files != null && files.length > 0, "classes not found");
		return files[0];
	}

	public List<File> collectFiles(File root, FileFilter filter) {
		List<File> files = new ArrayList<>();
		File[] fs = root.listFiles();
		for (File file : fs) {
			if (file.isDirectory()) {
				files.addAll(collectFiles(file,filter));
			}
			if (filter.accept(file)) {
				files.add(file);
			}
		}
		return files;
	}

	public Class<?> file2clazz(File root,File clazz) throws ClassNotFoundException{
		int prefixlen = root.getAbsolutePath().length()+1;
		String clazzname = clazz.getAbsolutePath().substring(prefixlen).replaceAll("/|\\\\", ".");
        clazzname = clazzname.substring(0, clazzname.length()-".class".length());
		return Class.forName(clazzname);
	}
	
	public String camel2underline(String prop){
		return prop.replaceAll("(?<Uper>[A-Z])", "_${Uper}").toLowerCase();
	}
	
	public <T, R> void processFiles(Function<T, R> function) {
		
	}
/*	public Map<String,Class<?>> getTableClassMap(){
		Map<String,Class<?>> map = new HashMap<>();
		map.put("vg_user",User.class);
	}*/
	public Connection connectDb() throws ClassNotFoundException, SQLException{
		String url="jdbc:postgresql://127.0.0.1:5432/test";
        String user="postgres";
        String password = "123456";
        Class.forName("org.postgresql.Driver");
        Connection connection= DriverManager.getConnection(url, user, password);
        return connection;
	}
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Generator gen = new Generator();
		File root = gen.getClassesDirectory();
		List<File> modelFiles = gen.collectFiles(root, new FileFilter() {
			@Override
			public boolean accept(File f) {
				return f.getPath().matches(".*/model/.+/class");
			}
		});
		List<Class<?>> models = modelFiles.stream().map(x->{
			try {
				return gen.file2clazz(root,x);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}).collect(Collectors.toList());
		//连接数据库获取
		Connection conn = gen.connectDb();
		PreparedStatement stmt = conn.prepareStatement("select schemaname,tablename from pg_tables where schemaname='public';");
		ResultSet rs = stmt.executeQuery();
		List<TableMeta> tables = new ArrayList<>();
		while(rs.next()){
			TableMeta tm = new TableMeta();
			tm.setSchema(rs.getString(1));
			tm.setName(rs.getString(2));
			tables.add(tm);
		}
		for(int i=0;i<tables.size();i++){
			TableMeta tm = tables.get(i);
			PreparedStatement columnStmt = conn.prepareStatement("select table_schema"
					+ "table_name,"
					+ "column_name,"
					+ "data_type,"
					+ "column_default,"
					+ "is_nullable"
					+ "from information_schema.columns"
					+ "where table_schema=? and table_name = ?;");
			columnStmt.setString(1, tm.getSchema());
			columnStmt.setString(2, tm.getName());
			ResultSet colRs = columnStmt.executeQuery();
			List<ColumnMeta> columns = new ArrayList<>();
			tm.setColumns(columns);
			while(colRs.next()){
				ColumnMeta cm = new ColumnMeta();
				cm.setSchema(colRs.getString(1));
				cm.setTable(colRs.getString(2));
				cm.setName(colRs.getString(3));
				cm.setTable(colRs.getString(4));
				cm.setDefaultvalue(colRs.getString(5));
				cm.setNullable(colRs.getString(6));
				columns.add(cm);
			}
		}
		Map<String,TableMeta> tableMetaMap = tables.stream().collect(Collectors.toMap(x->x.getName(), x->x));
		
		
		//获取model对应的表名
		//获取表的字段
		//获取model的属性
		//model字段驼峰转下划线
		//比较model属性和表的字段(个数及类型)
		//生成sql
		//输出到文件
		List<ModelMeta> modelMetas = new ArrayList<>();
		for(int i=0;i<models.size();i++){
			ModelMeta meta = new ModelMeta();
			Class<?> c = models.get(i);
			meta.setName(c.getSimpleName());
			Field[] fields = c.getDeclaredFields();
			List<FieldMeta> fms = new ArrayList<>();
			for(int j=0;j<fields.length;j++){
				FieldMeta fm = new FieldMeta();
				Field f = fields[i];
				fm.setName(f.getName());
				fm.setType(f.getType().getSimpleName());
				fms.add(fm);
			}
			meta.setFileds(fms);
		}
		
		
	}

}
