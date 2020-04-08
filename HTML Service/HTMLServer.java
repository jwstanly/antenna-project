import java.net.*;
import java.io.*;
import java.util.*;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class HTMLServer{

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
		
		//HOME/REDIRECT
		HttpContext redirectHomeContext = server.createContext("/");
			redirectHomeContext.setHandler(HTMLServer::handleIndexRequest);
	
		//INDEX
		HttpContext indexContext = server.createContext("/index");
			indexContext.setHandler(HTMLServer::handleIndexRequest);
		
		//CONFIGURE
		HttpContext configureContext = server.createContext("/configure");
			configureContext.setHandler(HTMLServer::handleConfigureRequest);
			
		//SET SEARCH
		HttpContext setSearchContext = server.createContext("/setSearch");
			setSearchContext.setHandler(HTMLServer::handleSetSearchRequest);
			
		//SELECT PLANES
		HttpContext selectPlanesContext = server.createContext("/selectPlanes");
			selectPlanesContext.setHandler(HTMLServer::handleSelectPlanesRequest);
		
		server.start();
	}
			
	private static void handleIndexRequest(HttpExchange exchange) throws IOException {
		String response = "";
		Scanner kb = new Scanner(new File("index.html"));
		while(kb.hasNextLine()){
			response+=kb.nextLine();
		}
		
		exchange.sendResponseHeaders(200, response.getBytes().length); //response code and length
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	private static void handleConfigureRequest(HttpExchange exchange) throws IOException {	
		String response = "";
		Scanner kb = new Scanner(new File("Configure.html"));
		while(kb.hasNextLine()){
			response+=kb.nextLine();
		}
		
		exchange.sendResponseHeaders(200, response.getBytes().length); //response code and length
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	private static void handleSetSearchRequest(HttpExchange exchange) throws IOException {	
		String response = "";
		Scanner kb = new Scanner(new File("SetSearch.html"));
		while(kb.hasNextLine()){
			response+=kb.nextLine();
		}
		
		exchange.sendResponseHeaders(200, response.getBytes().length); //response code and length
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
	
	private static void handleSelectPlanesRequest(HttpExchange exchange) throws IOException {	
		String response = "";
		Scanner kb = new Scanner(new File("SelectPlanes.html"));
		while(kb.hasNextLine()){
			response+=kb.nextLine();
		}
		
		exchange.sendResponseHeaders(200, response.getBytes().length); //response code and length
		OutputStream os = exchange.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}
}
