package com.myPackage.server;

import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import com.myPackage.client.GreetingService;
import com.myPackage.server.database.Client;
import com.myPackage.server.database.PMF;
import com.myPackage.shared.FieldVerifier;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	
	private static final Logger log = Logger.getLogger(GreetingServiceImpl.class.getName());

	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);
		
		nuevaVisita(input,new Date());

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	
	
	
	
	public void nuevaVisita(String username, Date date)
			throws IllegalArgumentException {

		
		PersistenceManager pm = null;
		pm = PMF.get().getPersistenceManager();

		Client users = new Client(username, date);
		
		Client e = null;
		try {

			Key k = KeyFactory.createKey(Client.class.getSimpleName(),
					username);
			e = pm.getObjectById(Client.class, k);
		} catch (Exception ex) {
			// Si el usuario no existe, salta una excpecion, por lo tanto se va
			// a insertar el nuevo usuario.
			pm.makePersistent(users);

		}

		if (e != null) {

			throw new IllegalArgumentException();

		}

		log.warning(" ***** Se incerto correctamente :" + username + " a la hora: "+ date);
		pm.close();


	}
	
	
	
	
	
	
	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
