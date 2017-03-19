package com.senghuotlim.services;

import com.senghuotlim.services.registration.RegistrationServer;
import com.senghuotlim.services.users.UsersServer;
import com.senghuotlim.services.web.WebServer;

/**
 * This is the main application to invoke other servers such as Registration Server, Users Server,
 * and Web Server. Also, you can specify the port you want the server to run on.
 *
 * Ex: java -jar target/microservice-dynamoDB-0.0.1-SNAPSHOT.jar users 5555
 */
public class Main {

	public static void main(String[] args) {
		String server = "Unknown Server";

		switch (args.length) {
			case 2:
				System.setProperty("server.port", args[1]);

			case 1:
				server = args[0].toLowerCase();
				break;

			default:
				error();
				break;
		}

		if (server.equals("reg") || server.equals("registration"))
			RegistrationServer.main(args);
		else if (server.equals("users"))
			UsersServer.main(args);
		else if (server.equals("web"))
			WebServer.main(args);
		else
			error();
	}

	protected static void error() {
		System.out.println("Usage: java -jar <jar file> <server-name> [server-port]");
		System.out.println("server-name: {'reg' or 'registration', 'accounts', 'web'} server-port > 1024");
	}
}
