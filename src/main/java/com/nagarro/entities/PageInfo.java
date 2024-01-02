package com.nagarro.entities;

import com.nagarro.repositories.UserTableRepo;

public class PageInfo {
	boolean hasPreviousPage;
	boolean hasNextPage;
	long total;
	
	public PageInfo() {}
	
	public PageInfo(boolean hasPreviousPage,boolean hasNextPage,long total) {
		this.hasNextPage=hasNextPage;
		this.hasPreviousPage=hasPreviousPage;
		this.total=total;
	}
	
	public PageInfo getPageInfo(long id,UserTableRepo userTableRepo,PageInfo pageInfo)
	{
		pageInfo.setHasNextPage(userTableRepo.existsById(id + 1));
		pageInfo.setHasPreviousPage(userTableRepo.existsById(id - 1));
		pageInfo.setTotal(userTableRepo.getTotalUserCount());
	    return pageInfo;
	}

	
	public boolean getHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public boolean getHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	
	

}
