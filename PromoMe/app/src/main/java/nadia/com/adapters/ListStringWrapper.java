package nadia.com.adapters;

import java.util.ArrayList;
import java.util.List;

public class ListStringWrapper {
	private List<String> list;

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> thelist) {
		this.list = thelist;
	}

	public ListStringWrapper(List<String> thelist) {
		list = thelist;
	}
	
	public ListStringWrapper(){
		list = new ArrayList<>();
	}
	
	public void add(String s){
		list.add(s);
	}
	
	
}
