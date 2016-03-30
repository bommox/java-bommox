package bommox.selenium.core;

import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="query")
public class QueryModel {
	
	String id;
	String search;
	Long minPrice;
	String titlelist;
	String blacklist;
	
	@XmlAttribute
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement
	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}
	
	@XmlElement
	public Long getMinPrice() {
		return minPrice;
	}
	
	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}
	
	@XmlElement
	public String getTitlelist() {
		return titlelist;
	}
	
	public void setTitlelist(String titlelist) {
		this.titlelist = titlelist;
	}
	
	@XmlElement
	public String getBlacklist() {
		return blacklist;
	}
	
	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}
	
	
	public List<String> getBlacklistArray() {
		return Arrays.asList(this.blacklist.split(","));
	}
	
	public List<String> getTitlelistArray() {
		return Arrays.asList(this.titlelist.split(","));
	}
	

}
