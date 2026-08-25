# Use case 1 - Applying YARRRML to enrich CKAN data sets using DCAT vocabulary
Each folder under [usecase1](./) contains the CKAN metadata statement files, the corresponding YARRRML mapping rules, the intermediary RML file and the generated output in Turtle (TTL) format. 
The section below provides additional information about the YARRRML mappings and explains how they can be executed to reproduce the results.

## YARRRML : Declarative Mapping Language

The semantic enrichment process via YARRRML involves a few steps. YARRRML rules are defined as the initial step of the semantic enrichment process, using input data and semantic links to standard ontologies as depicted in Figure \ref{YARRRMLProcess}. These definitions are then converted into RML through the [YARRRML parser](https://github.com/RMLio/yarrrml-parser). Following this conversion, an RML processor, such as [rmlmapper-java](https://github.com/rmlio/rmlmapper-java), transforms the resulting RML rules into RDF.
