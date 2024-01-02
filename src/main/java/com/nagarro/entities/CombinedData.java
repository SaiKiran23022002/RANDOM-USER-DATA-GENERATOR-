package com.nagarro.entities;

public class CombinedData {
    private UserTable data;
    private PageInfo pageInfo;

    public CombinedData(UserTable data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public UserTable getData() {
        return data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

	public void setData(UserTable data) {
		this.data = data;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	@Override
	public String toString() {
		return "CombinedData [data=" + data + ", pageInfo=" + pageInfo + ", getData()=" + getData() + ", getPageInfo()="
				+ getPageInfo() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public CombinedData() {
		super();
		
	}
    
    
}
