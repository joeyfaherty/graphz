package neo4j;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

/**
 * Created by joey on 2-6-16.
 */
public class GraphTest {

    public static void main(String[] args) {
        // default port is 7687
        Driver driver = GraphDatabase.driver("bolt://localhost", AuthTokens.basic("neo4j", "liverpoolFC1"));
        Session session = driver.session();

        //session.run("CREATE (a:Person {name:'Arthur', title:'King'})");

        try (Transaction tx = session.beginTransaction()) {
            tx.run("CREATE (a:Person {name:'Arthur', title:'King'})");
            tx.success();
        }

        StatementResult result = session.run("MATCH (a:Person) WHERE a.name = 'Arthur' return a.name AS name, a.title AS title");

        while (result.hasNext()) {
            Record record = result.next();
            System.out.println( record.get("title").asString() + " " + record.get("name").asString() );
        }
        session.close();
        driver.close();
    }
}
