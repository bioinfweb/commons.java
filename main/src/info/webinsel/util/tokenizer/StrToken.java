package info.webinsel.util.tokenizer;



public class StrToken {
  private int id = -1;
  private String content = "";
  private int pos = -1;
  
  
	public StrToken() {
		super();
	}


	public StrToken(int id, String content, int pos) {
		super();
		this.id = id;
		this.content = content;
		this.pos = pos;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public int getID() {
		return id;
	}


	public void setID(int id) {
		this.id = id;
	}



	public int getPos() {
		return pos;
	}


	public void setPos(int pos) {
		this.pos = pos;
	}
}
