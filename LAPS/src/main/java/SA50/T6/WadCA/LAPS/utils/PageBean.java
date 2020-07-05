package SA50.T6.WadCA.LAPS.utils;

public class PageBean {
	// page
	private int page;
	// Number of record per page
	private int pageSize;
	// first page
	private int start;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStart() {
		return (page - 1) * pageSize;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public PageBean() {
		super();
	}

	public PageBean(int page, int pageSize) {
		super();
		this.page = page;
		this.pageSize = pageSize;
	}

}


