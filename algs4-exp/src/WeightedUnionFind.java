public class WeightedUnionFind implements UnionFind {
    private final int[] root;
    private final int[] size;
    private int cnt;

    public WeightedUnionFind(int N) {
        root = new int[N];
        size = new int[N];
        cnt = N;

        for (int i = 0; i < N; i++) {
            root[i] = i;
            size[i] = 1;
        }
    }

    public int count() {
        return cnt;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        while (p != root[p]) {
            p = root[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (size[pRoot] > size[qRoot]) {
            root[qRoot] = pRoot;
            size[qRoot] += size[pRoot];
        } else {
            root[pRoot] = qRoot;
            size[pRoot] += size[qRoot];
        }
        cnt--;
    }
}
