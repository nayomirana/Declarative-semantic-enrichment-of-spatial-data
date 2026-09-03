###  RMLmapper-java 
[RMLMapper-java](https://github.com/RMLio/rmlmapper-java) is used to execute RML rules to generate RDF triples from the given data sources.
#### Prerequisites
* Java 17 is the minimum required version for compiling and running the current version of the project. <br>
* Development environments (IDEs) such as Visual Studio Code (VS Code), Eclipse IDE . <br>
* Apache Maven is required to be installed if you still need to install it. It can be done using [Homebrew](https://macpaw.com/how-to/install-maven-on-mac) .<br>

#### Installation steps 
* Clone the application from the [GitHub repository](https://github.com/RMLio/rmlmapper-java).  
* Build the application using the given command in the ReadMe file  (``` mvn install -DskipTests=true```  or  ```mvn test Dtest=!Mapper_OracleDB_Test```).
  
#### Execution
The following execution command should specify the relevant paths for the mapping and output files:
 ``` 
java -jar ./target/jarFile -m mappingFile.ttl -o output.ttl
 ```



#### Example:
The target location of each data source file in the RML mapping file must be updated to match the correct file paths on your local machine.

<pre><#ACTSitesSource> a rml:LogicalSource;
      rml:source "../Data/ACT.csv";  
      rml:referenceFormulation ql:CSV .</pre>
Modify the execution command as needed, specifying the locations of the JAR file, mapping file, and the destination for the output file. 
Example:
```
java -jar ./lib/rmlmapper-17.0.0-r449-all.jar -m ./src/PlaceNameKGAus/RML/PlaceNameMapping.ttl -o ./src/PlaceNameKGAus/out/pnkg_out.ttl
```
The PNKG in ttl file format will be stored in ```./src/PlaceNameKGAus/out/pnkg_out.ttl```


##### If the RML mapping uses custom Java functions, run the RMLMapper with the function definition file using:
```
java -jar rmlmapper.jar -m mapping.ttl -f functions.ttl -o output.ttl
```
#### Note:
Alternatively, you can use the provided [RMLmapper-java JAR file]() directly instead of installing and compiling the mapper using the steps above.
