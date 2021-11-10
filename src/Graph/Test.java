package Graph;

import java.util.*;

public class Test {
    public static Graph Test(int[][] matrix){
        Graph graph = new Graph();
        for(int i = 0; i <matrix.length; i++){
            Integer from = matrix[i][0];
            Integer to = matrix[i][1];
            Integer weight = matrix[i][2];
            if(!graph.nodes.containsKey(from)){
                graph.nodes.put(from, new Node(from));
            }
            if(!graph.nodes.containsKey(to)){
                graph.nodes.put(to, new Node(to));
            }
            Node fromNode = graph.nodes.get(from);
            Node toNode = graph.nodes.get(to);
            fromNode.out++;
            toNode.in++;
            Edge newEdge = new Edge(weight, fromNode, toNode);
            fromNode.nexts.add(toNode);
            fromNode.edges.add(newEdge);
            graph.edges.add(newEdge);
        }
        return graph;
    }

    // 并查集模拟
    public static class MySets {

        public HashMap<Node, List<Node>> setMap = new HashMap<>();

        public MySets(List<Node> nodes){
            for(Node cur: nodes){
                List<Node> set = new ArrayList<>();
                set.add(cur);
                setMap.put(cur, set);
            }
        }

        public boolean isSameSet(Node from, Node to){
            List<Node> fromSet = setMap.get(from);
            List<Node> toSet = setMap.get(to);
            return fromSet == toSet;
        }

        public List<Node> union(Node from, Node to){
            List<Node> fromSet = setMap.get(from);
            List<Node> toSet = setMap.get(to);
            for(Node node: toSet){
                fromSet.add(node);
                setMap.put(node, fromSet);
            }
            return fromSet;
        }
    }

    // 图的广度优先遍历
    public static void graphBfs(Node node){
        if(node == null){
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(node);
        set.add(node);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.value);
            for(Node next : cur.nexts){
                if(!set.contains(next)){
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }

    // 图的深度优先遍历
    public static void graphDfs(Node node){
        if(node == null){
            return;
        }
        HashSet<Node> set = new HashSet<>();
        Stack<Node> stack = new Stack<>();
        stack.push(node);
        set.add(node);
        System.out.println(node.value);
        while (!stack.empty()){
            Node cur = stack.pop();
            for(Node next: cur.nexts){
                if(!set.contains(next)){
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }

    // 拓扑排序
    // 循环减入度
    public static List<Node> sortedTopology(Graph graph){
        HashMap<Node, Integer> inMap = new HashMap<>();
        Queue<Node> zeroInQueue = new LinkedList<>();
        for(Node node: graph.nodes.values()){
            inMap.put(node, node.in);
            if(node.in == 0){
                zeroInQueue.add(node);
            }
        }

        List<Node> results = new ArrayList<>();
        while (!zeroInQueue.isEmpty()){
            Node cur = zeroInQueue.poll();
            System.out.println(cur.value);
            results.add(cur);
            for(Node next: cur.nexts){
                inMap.put(next, inMap.get(next) - 1);
                if(inMap.get(next) == 0){
                    zeroInQueue.add(next);
                }
            }
        }
        return results;
    }

    public static class EdgeComparator implements Comparator<Edge>{
        @Override
        public int compare(Edge o1, Edge o2){
            return o1.weight - o2.weight;
        }
    }

    // kruskal算法
    // 寻找最小路径
    public static Set<Edge> kruskalMST(Graph graph){
        MySets unionFind = new MySets(new ArrayList<>(graph.nodes.values()));
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for(Edge edge: graph.edges){
            priorityQueue.add(edge);
        }
        Set<Edge> result = new HashSet<>();
        while (!priorityQueue.isEmpty()){
            Edge edge = priorityQueue.poll();
            if(!unionFind.isSameSet(edge.from, edge.to)){
                System.out.println(edge.weight);
                result.add(edge);
                unionFind.union(edge.from, edge.to);
            }
        }
        return result;
    }

    // prim算法
    public static Set<Edge> primeMST(Graph graph){
        HashSet<Node> visitedSet = new HashSet<>();
        HashSet<Edge> result = new HashSet<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for(Node node: graph.nodes.values()){
            if(!visitedSet.contains(node)){
                visitedSet.add(node);
                for(Edge edge: node.edges){
                    priorityQueue.add(edge);
                }
                while (!priorityQueue.isEmpty()){
                    Edge edge = priorityQueue.poll();
                    Node toNode = edge.to;
                    if(!visitedSet.contains(toNode)){
                        visitedSet.add(toNode);
                        result.add(edge);
                        for(Edge nextEdge: toNode.edges){
                            System.out.println(nextEdge.weight);
                            priorityQueue.add(nextEdge);
                        }
                    }
                }
            }
        }
        return result;
    }

    // dijkstra算法
    public static HashMap<Node,Integer> dijkstraMST(Node head){
        HashMap<Node, Integer> distanceMap = new HashMap<>();
        distanceMap.put(head, 0);

        HashSet<Node> selectedSet = new HashSet<>();
        // 获取路径中的最小路径
        Node minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedSet);
        while(minNode != null){
            int distance = distanceMap.get(minNode);
            for(Edge edge: minNode.edges){
                Node toNode = edge.to;
                if(!distanceMap.containsKey(toNode)){
                    distanceMap.put(toNode, distance + edge.weight);
                }
                distanceMap.put(toNode, Math.min(distanceMap.get(toNode), distance + edge.weight));
            }
            selectedSet.add(minNode);
            minNode = getMinDistanceAndUnselectedNode(distanceMap, selectedSet);
        }
        for(Map.Entry<Node, Integer> entry: distanceMap.entrySet()){
            Node node = entry.getKey();
            int distance = entry.getValue();
            System.out.println((char)node.value);
            System.out.println(distance);
        }
        return distanceMap;
    }

    public static Node getMinDistanceAndUnselectedNode(HashMap<Node, Integer> distanceMap, HashSet<Node> touchedNodes){
        // 循环distanceMap,找到最小的
        Integer minDistance = Integer.MAX_VALUE;
        Node minNode = null;
        for(Map.Entry<Node, Integer> entry: distanceMap.entrySet()){
            Node node = entry.getKey();
            int distance = entry.getValue();
            if(!touchedNodes.contains(node) && distance < minDistance){
                minDistance = distance;
                minNode = node;
            }
        }
        return minNode;
    }

    public static void main(String[] args){
//        int[][] matrix = {
//                {1, 2, 4},
//                {1, 5, 2},
//                {1, 4 ,5},
//                {2, 5, 3},
//                {2, 3, 6},
//                {3, 5, 7},
//                {3, 4, 2},
//                {4, 5, 1}
//         };
//        Graph graph = Test(matrix);
//        graphDfs(graph.nodes.get(1));
        int[][] matrix = {
                {'A', 'B', 3},
                {'A', 'C', 15},
                {'A', 'D', 9},
                {'B', 'C', 2},
                {'B', 'E', 200},
                {'C', 'D', 7},
                {'C', 'E', 14},
                {'D', 'E', 16},
        };
        Graph graph = Test(matrix);
        dijkstraMST(graph.nodes.get(65));
    }
}
