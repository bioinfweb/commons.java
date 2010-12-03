package info.webinsel.util.servlet.urltree;



public class ErrorPageData {
  private int code;
  private String head = "";
  private String onLoad = "";
  private String body = "";
  
  
	public ErrorPageData(int code) {
		super();
		this.code = code;
	}


	public String getBody() {
		return body;
	}


	public void setBody(String body) {
		this.body = body;
	}


	public String getHead() {
		return head;
	}


	public void setHead(String head) {
		this.head = head;
	}


	public String getOnLoad() {
		return onLoad;
	}


	public void setOnLoad(String onLoad) {
		this.onLoad = onLoad;
	}


	public int getCode() {
		return code;
	}
}
