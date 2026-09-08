### Configure Data Source Connections

Before running the RML mappings, update the database and SPARQL endpoint connection details in the corresponding RML files.

1. **PostgreSQL database connection**

   For Step 1, configure `<#DB_source>` with the connection details of the PostgreSQL database containing the given hydro spatial data.
   Update the following properties as required:

   ```turtle
       <#DB_source> a d2rq:Database;
           d2rq:jdbcDSN "jdbc:postgresql://<host>:<port>/<database>";
           d2rq:jdbcDriver "org.postgresql.Driver";
           d2rq:username "<username>";
           d2rq:password "<password>" .
   ```

3. **GraphDB connection**

   For Steps 2, 3, and 4, update the GraphDB SPARQL endpoint URLs in the corresponding RML files.

   The output SPARQL endpoint is configured using `<#SPARQLEndpoint>`:

   ```turle
      <#SPARQLEndpoint> a sd:Service;
         sd:endpoint <http://<host>:<port>/repositories/<repository>/statements> ;
         sd:supportedLanguage sd:SPARQL11Update .
   ```
   
   Where an existing GraphDB repository is used as an input source, configure `<#InputSPARQL>`:

   ```turtle
   <#InputSPARQL>
       a sd:Service ;
       sd:endpoint <http://<host>:<port>/repositories/<repository>> ;
       sd:supportedLanguage sd:SPARQL11Query ;
       sd:resultFormat <http://www.w3.org/ns/formats/SPARQL_Results_JSON> .
   ```

Replace `<host>`, `<port>`, `<database>`, `<repository>`, `<username>`, and `<password>` with the relevant values.

