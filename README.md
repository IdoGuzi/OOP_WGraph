# WGraph - weighted graph

 ## About the Project
This repo is built as part of Object Oriented Programing course.  
The focus of this project is to create an efficient and easy to use graph library.
 
 ## Table of Contents
 * [About the Project](#about-the-project)
 * [Installation](#installation)
 * [Usage](#usage)
 
 ## Installation
Can be downloaded as a zip or from git with:
 ```bash
git clone https://github.com/IdoGuzi/OOP_WGraph.git
```

In the lib folder there's a json jar, make sure to add it to the project.  
make sure that Junit 5.7.0 is added to the project for the testers.

## Usage

for full explanation check the wiki.
```java
weighted_graph g = new WGraph_DS();
g.addNode(5);
g.addNode(3);
g.connect(5,3,4.3); //connecting node-5 with node-3 with weight of 4.3
weighted_graph_algorithms ga = new WGraph_Algo(g);
double dist = ga.shortestPathDist(3,5); // will return 4.3
```