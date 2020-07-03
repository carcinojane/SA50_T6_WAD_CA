package SA50.T6.WadCA.LAPS.utils;

public class PageUtil {
	/**
	 * Generate pagination code
	 * @param targetUrl   
	 * @param totalNum    
	 * @param currentPage 
	 * @param pageSize    
	 * @return
	 */
	public static String genPagination(String targetUrl, long totalNum, int currentPage, int pageSize, String param) {
		long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
		if (totalPage == 0) {
			return "No relevent data";
		} else {
			StringBuffer pageCode = new StringBuffer();
			pageCode.append("<li><a href='" + targetUrl + "?page=1&" + param + "'>Homepage</a></li>");
			if (currentPage > 1) {
				pageCode.append(
						"<li><a href='" + targetUrl + "?page=" + (currentPage - 1) + "&" + param + "'>previous page</a></li>");
			} else {
				pageCode.append("<li class='disabled'><a href='" + targetUrl + "?page=" + (currentPage - 1) + "&"
						+ param + "'>previous page</a></li>");
			}
			for (int i = currentPage - 2; i <= currentPage + 2; i++) {
				if (i < 1 || i > totalPage) {
					continue;
				}
				if (i == currentPage) {
					pageCode.append("<li class='active'><a href='" + targetUrl + "?page=" + i + "&" + param + "'>" + i
							+ "</a></li>");
				} else {
					pageCode.append("<li><a href='" + targetUrl + "?page=" + i + "&" + param + "'>" + i + "</a></li>");
				}
			}
			if (currentPage < totalPage) {
				pageCode.append(
						"<li><a href='" + targetUrl + "?page=" + (currentPage + 1) + "&" + param + "'>next page</a></li>");
			} else {
				pageCode.append("<li class='disabled'><a href='" + targetUrl + "?page=" + (currentPage + 1) + "&"
						+ param + "'>next page</a></li>");
			}
			pageCode.append("<li><a href='" + targetUrl + "?page=" + totalPage + "&" + param + "'>last page </a></li>");
			return pageCode.toString();
		}
	}

}
