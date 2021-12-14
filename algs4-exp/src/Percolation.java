public class Percolation {
    private final boolean[] map;
    private final UnionFind wuf;
    private final int N;
    private final int BOTTOM_IDX;
    public int count = 0;

    /**
     * Initialize a simulation
     *
     * @param N size of the square grid above 0
     */
    public Percolation(int N, UFType type) {
        if (N <= 0)
            throw new IllegalArgumentException("N should be positive, while " + N + " is given");

        this.N = N;
        this.BOTTOM_IDX = N * N + 1;
        map = new boolean[N * N + 2];
        //wuf = new WeightedUnionFind(N * N + 2);
        if (type == UFType.FastUnionFind)
            wuf = new FastUnionFind(N * N + 2);
        else
            wuf = new WeightedUnionFind(N * N + 2);

        map[0] = map[N * N + 1] = true;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(StdIn.readInt(), UFType.WeightedUnionFind);
        while (!p.percolates()) {
            int x, y;
            x = StdIn.readInt();
            y = StdIn.readInt();
            p.open(x, y);

            StdOut.println("------------------------------");
            for (int j = 1; j <= p.N * p.N; j++) {
                if (!p.map[j])
                    StdOut.print("#");
                else
                    StdOut.print(" ");
                if (j % p.N == 0)
                    StdOut.println();
            }
            StdOut.println("------------------------------");

            if (p.percolates()) {
                StdOut.println("PERCOLATES NOW");
                System.exit(0);
            }
        }
    }

    /**
     * Calculate the index in the array map for a given coordinate, which is ASSUMED AS VALID
     *
     * @param i row of the point
     * @param j column of the point
     * @return index of the point in array map
     */
    private int toIndex(int i, int j) {
        return (i - 1) * N + j;
    }

    private boolean isInRange(int i, int j) {
        int t = toIndex(i, j);
        return (t <= N * N) && (t >= 1) && (i >= 1) && (i <= N) && (j >= 1) && (j <= N);
    }

    /**
     * Mark a point as open, and connect it with surroundings
     *
     * @param i row
     * @param j column
     */
    public void open(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("i");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("j");

        int idx = toIndex(i, j);
        map[idx] = true;

        if (i == 1) {
            wuf.union(idx, 0);
        }
        if (i == N) {
            wuf.union(idx, map.length - 1);
        }

        if (isInRange(i - 1, j) && map[toIndex(i - 1, j)]) {
            wuf.union(idx, toIndex(i - 1, j));
        }
        if (isInRange(i, j - 1) && map[toIndex(i, j - 1)]) {
            wuf.union(idx, toIndex(i, j - 1));
        }
        if (isInRange(i, j + 1) && map[toIndex(i, j + 1)]) {
            wuf.union(idx, toIndex(i, j + 1));
        }
        if (isInRange(i + 1, j) && map[toIndex(i + 1, j)]) {
            wuf.union(idx, toIndex(i + 1, j));
        }
        count++;
    }

    /**
     * Check weather a point is marked as open
     *
     * @param i row
     * @param j column
     * @return weather this point is marked as open
     */
    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("Invalid i");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("Invalid j");

        return map[toIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        if (i <= 0 || i > N)
            throw new IndexOutOfBoundsException("Invalid i");
        if (j <= 0 || j > N)
            throw new IndexOutOfBoundsException("Invalid j");

        return !map[toIndex(i, j)];
    }

    /**
     * Check weather a system percolates now
     *
     * @return weather the system percolates now
     */
    public boolean percolates() {
        return wuf.connected(0, BOTTOM_IDX);
    }
}
