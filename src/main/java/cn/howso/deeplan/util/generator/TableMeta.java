package cn.howso.deeplan.util.generator;

import java.util.List;

public class TableMeta {
	private String schema;
	private String name;
	private List<ColumnMeta> columns;
	public String getSchema() {
		return schema;
	}
	public void setSchema(String schema) {
		this.schema = schema;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ColumnMeta> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnMeta> columns) {
		this.columns = columns;
	}
	
}
