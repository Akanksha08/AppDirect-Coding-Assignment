package product.beans;

/**
 * @author akanksha
 *
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book", namespace = "http://apna-pustakalaya.com")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String author;

	/**
	 * needed by JAXB
	 */
	public Book(){}

	public Book(int id, String name, String author) {
		this.id = id;
		this.name = name;
		this.author = author;
	}
	
	public int getId() {
		return id;
	}

	@XmlElement
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	@XmlElement
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAuthor() {
		return author;
	}
	
	@XmlElement
	public void setAuthor(String author) {
		this.author = author;
	}		
}
