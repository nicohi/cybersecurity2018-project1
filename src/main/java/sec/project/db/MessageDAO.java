package sec.project.db;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.h2.tools.RunScript;
import org.springframework.stereotype.Component;
import sec.project.domain.Message;

@Component
public class MessageDAO {
	String databaseAddress;

	Connection connection;

	public MessageDAO() {
		databaseAddress = "jdbc:h2:file:./db/database";

		try {
			connection = DriverManager.getConnection(databaseAddress, "sa", "");
		} catch (SQLException ex) {
            System.err.println(ex.getMessage());
		}
        try {
            // If database has not yet been created, insert content
            RunScript.execute(connection, new FileReader("sql/database-schema.sql"));
            RunScript.execute(connection, new FileReader("sql/database-import.sql"));
        } catch (Throwable t) {
            System.err.println(t.getMessage());
        }
	}

	public boolean runQuery(String s) {
		System.out.println(s);
		try {
			connection.createStatement().execute(s);
			return true;
		} catch (SQLException ex) {
            System.err.println(ex.getMessage());
			return false;
		}
	}

	public List<Message> getMessages() {
		try {
			ArrayList<Message> msgs = new ArrayList<>();
			ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM Message");
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				String content = resultSet.getString("content");
				msgs.add(new Message(name, content));
			}
			return msgs;
		} catch (SQLException ex) {
            System.err.println(ex.getMessage());
			return new ArrayList<>();
		}
	}

		

}
