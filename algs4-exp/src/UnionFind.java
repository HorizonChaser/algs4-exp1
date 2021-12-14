public interface UnionFind {
    void union(int i, int j);
    int find(int j);
    boolean connected(int i, int j);
    int count();
}
