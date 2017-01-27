package product.service;

import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.Descriptions;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;

import product.beans.Book;
import product.beans.BookDao;

@Description(value = "Book Service to integrate with AppDirect")
@Path("/api")
public class BookService {
	private static Logger logger = Logger.getLogger(BookService.class.getName());
	BookDao bookDao = new BookDao();

	@GET
	@Path("/books/{name}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Descriptions({ 
		@Description(value = "Gives a particular book by name in 2 formats(xml/json)", target = DocTarget.METHOD),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
	})
	public Book getBook(@PathParam("name") String name) throws Exception {
		logger.info("get book : " + name + " called");
		bookDao.addBook(0, "dummy", "dummy");
		return bookDao.getBookByName(name);
	}
	
	@PUT
	@Path("/books")
	@Consumes(MediaType.APPLICATION_XML)
	@Descriptions({ 
		@Description(value = "Adds a new book", target = DocTarget.METHOD),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
	})
	public void addBook(Book book) throws Exception {
		logger.info("Add book " + book + "called");
		bookDao.addBook(book.getId(), book.getName(), book.getAuthor());
	}

	@DELETE
	@Path("/books")
	@Descriptions({ 
		@Description(value = "Deletes a particular book by name", target = DocTarget.METHOD),
		@Description(value = "Request", target = DocTarget.REQUEST),
		@Description(value = "Response", target = DocTarget.RESPONSE),
	})
	public Response deleteBook(@QueryParam("name") String name) {
		logger.info("Delete book: " + name + " called");
		if (bookDao.removeBook(name)) {
			return Response
					.status(200)
					.entity("Book: " + name + " has been removed").build();
		}
		return Response
				.status(400)
				.entity("Unable to find the requested book: " + name).build();
	}
	
}

