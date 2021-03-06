# COMP 476 Assignment 2

Created on Unity 2020.2.1f1

## Pathfinding

### Getting it running

---

Everything is contained in the `yes` scene, and activated through buttons in the `Pathfinder` GameObject's inspector. First you have to chose which graph to use, either the grid based tiled graph by pressing `Build Grid` or the PoV based graph by pressing `Build Graph`. Once one of those are selected, you will see the Gizmos in the Scene view display the respective graphs. After a graph is selected, press the respective `Running Time` buttons to start the pathfinding and have the Gizmos display the path found. After starting the pathfinding, you can select the heuristic you would like to use by pressing their respective buttons: `Null Heuristic`, `Euclidean Distance Heuristic`, or `Cluster Heuristic`. This selection can be changed live.

After you start the pathfinding, the NPC will start to follow to path towards the placed goal through these graphs.

### NPC Behaviour

---

The NPC will follow the path that is generated by the A* algorithm and once the NPC reaches close enough to the goal node (to be considered on the same node), the program will pick a new random node to become the new goal.

WARNING: Right now, the grid path works perfectly fine, but the graph path results in the NPC going through the walls and false recalculation of the path (since it picks the closed node to the NPC's transform)

### Fill Screenshots

---

These are the fill screenshots for the different algorithms. Pink encompasses the closed set, Yellow is the open set, White are the unvisited nodes, and Black is the path that is found.

#### Grid Null Heuristic

![Grid Null Heuristic](/images/GridNull.png)

#### Grid Euclidean Distance Heuristic

![Grid Euclidean Heuristic](/images/GridEuclid.png)

#### Grid Clustering Heuristic

![Grid Cluster Heuristic](/images/GridCluster.png)

---
---
---

#### Graph Null Heuristic

![Graph Null Heuristic](/images/GraphNull.png)

#### Graph Euclidean Distance Heuristic

![Graph Euclidean Heuristic](/images/GraphEuclid.png)

#### Graph Clustering Heuristic

![Graph Cluster Heuristic](/images/GraphCluster.png)
