public class FastUnionFind implements UnionFind {
    private final int[] root;
    private int cnt;

    FastUnionFind(int N) {
        cnt = N;
        root = new int[N];
        for(int i = 0;i < N; i++)
            root[i] = i;
    }

    @Override
    public void union(int i, int j) {
        int iRoot = root[i], jRoot = root[j];
        if(iRoot == jRoot)
            return;
        for(int x = 0; x < root.length; x++) {
            if (root[x] == iRoot)
                root[x] = jRoot;
        }
        cnt--;
    }

    @Override
    public int find(int j) {
        return root[j];
    }

    @Override
    public boolean connected(int i, int j) {
        return root[i] == root[j];
    }

    @Override
    public int count() {
        return cnt;
    }
}
