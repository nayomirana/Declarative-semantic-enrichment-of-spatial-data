# Use case 1 - Applying YARRRML to enrich CKAN data sets using DCAT vocabulary
## Repository structure
Each folder under [usecase1](./) contains the CKAN metadata statement files, the corresponding YARRRML mapping rules, the intermediary RML file and the generated output in Turtle (TTL) format. 
The section below provides additional information about the YARRRML mappings and explains how they can be executed to reproduce the results.




## Semantic Enrichment Using YARRRML: A Declarative Mapping Language

The semantic enrichment process via YARRRML involves a few steps. YARRRML rules are defined as the initial step of the semantic enrichment process, using input data and semantic links to standard ontologies as depicted in following figure. These definitions are then converted into RML through the [YARRRML parser](https://github.com/RMLio/yarrrml-parser). Following this conversion, an RML processor, such as [rmlmapper-java](https://github.com/rmlio/rmlmapper-java), transforms the resulting RML rules into RDF.



<div align="center">
  <img src="images/YARRRML_sde_process.png" alt="YARRRML process" style="width:75%;">
</div>

## Reproducing the Outputs

To reproduce the provided outputs using the given input data and YARRRML mappings, run the following commands:

## Key resources 
- [YARRRML Specification](https://rml.io/yarrrml/spec/)
- [YARRRML Parser](https://github.com/RMLio/yarrrml-parser)
- [Tutorial: generating Linked Data with YARRRML](https://rml.io/yarrrml/tutorial/getting-started/)
