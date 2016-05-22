# Multithread CentiScaPe
Google Summer of Code 2016 project for National Resource for Network Biology  

Student: Ivan Bestvina  
Primary mentor: Giovanni Scardoni  
Secondary mentors: Shaik Faizaan, Gabriele Tosadori  

### About
CentiScaPe is a Cytoscape application used to calculate a number of different node centralities in a network. All of the centrality calculating algorithms are currently implemented in a single thread, while most of them would be suitable for parallelization, which would considerably increase their speed, thus consistently improving the performance of the CentiScaPe.
Main goal of this project is to parallelize and optimize the All-Pairs-Shortest-Path problem solver upon which most of the centralities rely. Two methods are considered: all-pairs Dijsktra's algorithm, and Floyd-Warshall algorithm, with their strengths and weaknesses compared. Apart from the parallelization, some other optimizations are considered.
