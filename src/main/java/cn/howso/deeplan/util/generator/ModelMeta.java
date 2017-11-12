package cn.howso.deeplan.util.generator;

import java.util.List;

public class ModelMeta {
	private String name;
	private List<FieldMeta> fileds;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<FieldMeta> getFileds() {
		return fileds;
	}
	public void setFileds(List<FieldMeta> fileds) {
		this.fileds = fileds;
	}
}
