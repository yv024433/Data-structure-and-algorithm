import java.util.*;

/**
 * @author Andrew "Gozz" Gozzard <andrew.gozzard@uwa.edu.au>
 *
 * Editorial implementation of 2020 CITS2200 project.
 *
 * All questions work with a greyscale image specified as a 2D int array.
 * The array is indexed first by row, then by column.
 * Every row in the array will be the same length.
 * Every element in the array will be non-negative and no greater than 255.
 * A value of 0 represents a black pixel, and a value of 255 represents white, with shades of grey in between.
 * 
 * Time complexity specifications use R for number of rows, C for number of columns, and P = R*C for number of pixels.
 */
public class EditorialProject implements Project {
    /**
     * Compute the number of pixels that change when performing a black flood-fill from the pixel at (row, col) in the given image.
     * A flood-fill operation changes the selected pixel and all contiguous pixels of the same colour to the specified colour.
     * A pixel is considered part of a contiguous region of the same colour if it is exactly one pixel up/down/left/right of another pixel in the region.
     *
     * @param image The greyscale image as defined above
     * @param row The row index of the pixel to flood-fill from
     * @param col The column index of the pixel to flood-fill from
     * @return The number of pixels that changed colour when performing this operation
     */
    public int floodFillCount(int[][] image, int row, int col) {
        // return floodFillCountRecursive(image, row, col);
        return floodFillCountBreadthFirst(image, row, col);
    }

    // Recursive Depth First Search
    public int floodFillCountRecursive(int[][] image, int row, int col) {
        int colour = image[row][col];
        if (colour == 0) {  // If this pixel is already black, there is nothing to change
            return 0;
        }
        image[row][col] = 0;

        int rows = image.length, cols = image[0].length;    // Extract number of rows and columns

        int count = 1;  // We have already changed this pixel, so count starts at 1

        // Check bounds and recurse in each direction, adding to total
        if (row-1 >= 0 && image[row-1][col] == colour)
            count += floodFillCountRecursive(image, row-1, col);

        if (row+1 < rows && image[row+1][col] == colour)
            count += floodFillCountRecursive(image, row+1, col);

        if (col-1 >= 0 && image[row][col-1] == colour)
            count += floodFillCountRecursive(image, row, col-1);

        if (col+1 < cols && image[row][col+1] == colour)
            count += floodFillCountRecursive(image, row, col+1);

        return count;
    }

    // Iterative Breadth First Search
    public int floodFillCountBreadthFirst(int[][] image, int row, int col) {
        int initial_colour = image[row][col];

        int rows = image.length, cols = image[0].length;

        Queue<Integer> queue = new ArrayDeque<Integer>();   // Pixels that still need exploring
        boolean[][] seen = new boolean[rows][cols]; // Pixels that are in the queue or the tree

        if (initial_colour != 0) {
            queue.add(row * cols + col);    // Convert coordinates into unique id
            seen[row][col] = true;
        }

        int result = 0;

        // BFS through pixel-adjacency graph
        while (!queue.isEmpty()) {
            int id = queue.poll();
            int r = id / cols;
            int c = id % cols;

            result++;

            // Explore cardinal neighbours
            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};
            for (int i = 0; i < dr.length; i++) {
                int nr = r + dr[i], nc = c + dc[i];
                if (nr < 0 || rows <= nr || nc < 0 || cols <= nc) continue; // Ignore if neighbour is outside image
                if (seen[nr][nc] || image[nr][nc] != initial_colour) continue;   // Only move to unseen neighours of same colour
                seen[nr][nc] = true;
                queue.add(nr * cols + nc);
            }
        }

        return result;
    }

    /**
     * Compute the total brightness of the brightest exactly k*k square that appears in the given image.
     * The total brightness of a square is defined as the sum of its pixel values.
     * You may assume that k is positive, no greater than R or C, and no greater than 2048.
     *
     * @param image The greyscale image as defined above
     * @param k the dimension of the squares to consider
     * @return The total brightness of the brightest square
     */
    public int brightestSquare(int[][] image, int k) {
        // return brightestSquareNaive(image, k);
        // return brightestSquareSlidingWindow(image, k);
        // return brightestSquareSlidingWindow2D(image, k);
        return brightestSquareSumArray(image, k);
    }

    public int brightestSquareNaive(int[][] image, int k) {
        int result = -1;

        int rows = image.length, cols = image[0].length;

        // Consider all squares in image
        for (int row = 0; row + k <= rows; row++) {
            for (int col = 0; col + k <= cols; col++) {
                // Compute sum of each square
                int brightness = 0;
                for (int r = row; r < row + k; r++) {
                    for (int c = col; c < col + k; c++) {
                        brightness += image[r][c];
                    }
                }
                // Keep track of brightest square seen so far
                result = Math.max(result, brightness);
            }
        }

        return result;
    }

    public int brightestSquareSlidingWindow(int[][] image, int k) {
        int result = -1;

        int rows = image.length, cols = image[0].length;

        for (int row = 0; row + k <= rows; row++) {
            int brightness = 0;
            for (int col = 0; col < cols; col++) {
                for (int r = row; r < row + k; r++) {
                    brightness -= col < k ? 0 : image[r][col-k];    // Remove old column from square, if it exists
                    brightness += image[r][col];    // Add new column to square
                }
                result = Math.max(result, brightness);  // Maintain running maximum
            }
        }

        return result;
    }

    public int brightestSquareSlidingWindow2D(int[][] image, int k) {
        int result = -1;

        int rows = image.length, cols = image[0].length;

        int[] segsums = new int[cols];
        for (int row = 0; row < rows; ++row) {
            int brightness = 0;
            for (int col = 0; col < cols; ++col) {
                segsums[col] -= row < k ? 0 : image[row-k][col];    // Remove old pixel from column, if it exists
                segsums[col] += image[row][col];    // Add new pixel to column
                brightness -= col < k ? 0 : segsums[col-k]; // Remove old column from square, if it exists
                brightness += segsums[col]; // Add new column to square
                result = Math.max(result, brightness);  // Maintain running maximum
            }
        }

        return result;
    }

    public int brightestSquareSumArray(int[][] image, int k) {
        int result = -1;

        int rows = image.length, cols = image[0].length;

        // Build 2D cumulative sum array
        int[][] sums = new int[rows+1][cols+1];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                sums[row+1][col+1] = image[row][col]
                                   + sums[row][col+1]
                                   + sums[row+1][col]
                                   - sums[row][col];
            }
        }

        // Query all k*k squares using cumulative sum array
        for (int row = 0; row + k <= rows; row++) {
            for (int col = 0; col + k <= cols; col++) {
                int brightness = sums[row+k][col+k]
                               - sums[row][col+k]
                               - sums[row+k][col]
                               + sums[row][col];

                result = Math.max(result, brightness);
            }
        }

        return result;
    }

    /**
     * Compute the maximum brightness that MUST be encountered when drawing a path from the pixel at (ur, uc) to the pixel at (vr, vc).
     * The path must start at (ur, uc) and end at (vr, vc), and may only move one pixel up/down/left/right at a time in between.
     * The brightness of a path is considered to be the value of the brightest pixel that the path ever touches.
     * This includes the start and end pixels of the path.
     *
     * @param image The greyscale image as defined above
     * @param ur The row index of the start pixel for the path
     * @param uc The column index of the start pixel for the path
     * @param vr The row index of the end pixel for the path
     * @param vc The column index of the end pixel for the path
     * @return The minimum brightness of any path from (ur, uc) to (vr, vc)
     */
    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc) {
        // return darkestPathRepeatedDFS(image, ur, uc, vr, vc);
        // return darkestPathBinarySearchDFS(image, ur, uc, vr, vc);
        // return darkestPathPFS(image, ur, uc, vr, vc);
        return darkestPathPrims(image, ur, uc, vr, vc);
    }

    public int darkestPathRepeatedDFS(int[][] image, int ur, int uc, int vr, int vc) {
        int rows = image.length, cols = image[0].length;

        int threshold = Math.max(image[ur][uc], image[vr][vc]); // Path must be at least as bright as start and end

        while (true) {  // Run a DFS with current threshold
            Stack<Integer> stack = new Stack<Integer>();
            boolean[][] seen = new boolean[rows][cols];

            stack.push(ur * cols + uc);
            seen[ur][uc] = true;

            while (!stack.isEmpty()) {
                int id = stack.pop();
                int r = id / cols;
                int c = id % cols;

                if (r == vr && c == vc) {
                    return threshold;   // If we have found v for the first time this must be the solution
                }

                int[] dr = {-1, 1, 0, 0};
                int[] dc = {0, 0, -1, 1};
                for (int i = 0; i < dr.length; i++) {
                    int nr = r + dr[i], nc = c + dc[i];
                    if (nr < 0 || rows <= nr || nc < 0 || cols <= nc) continue;
                    if (seen[nr][nc] || image[nr][nc] > threshold) continue;
                    seen[nr][nc] = true;
                    stack.push(nr * cols + nc);
                }
            }

            threshold++;    // If the DFS finished without finding v, increase it and retry
        }
    }

    public int darkestPathBinarySearchDFS(int[][] image, int ur, int uc, int vr, int vc) {
        int rows = image.length, cols = image[0].length;

        int lower = Math.max(image[ur][uc], image[vr][vc]);
        int upper = lower;
        for (int r = 0; r < rows; ++r) {
            for (int c = 0; c < cols; ++c) {
                upper = Math.max(upper, image[r][c]);
            }
        }

        // Binary search until bounds converge
        while (lower < upper) {
            int threshold = (lower + upper) / 2;

            Stack<Integer> stack = new Stack<Integer>();
            boolean[][] seen = new boolean[rows][cols];

            stack.push(ur * cols + uc);
            seen[ur][uc] = true;

            while (!stack.isEmpty()) {
                int id = stack.pop();
                int r = id / cols;
                int c = id % cols;

                int[] dr = {-1, 1, 0, 0};
                int[] dc = {0, 0, -1, 1};
                for (int i = 0; i < dr.length; i++) {
                    int nr = r + dr[i], nc = c + dc[i];
                    if (nr < 0 || rows <= nr || nc < 0 || cols <= nc) continue;
                    if (seen[nr][nc] || image[nr][nc] > threshold) continue;
                    seen[nr][nc] = true;
                    stack.push(nr * cols + nc);
                }
            }

            if (seen[vr][vc]) {
                upper = threshold;  // If we found v then this threshold must be no less than the minimum viable
            } else {
                lower = threshold + 1;  // If not, we know the minimum viable must be greater than this
            }
        }

        return lower;
    }

    // Priority Queue State for darkestPathPFS
    private class PathState implements Comparable<PathState> {
        public int priority;
        public int row, col;

        public PathState(int p, int r, int c) {
            priority = p;
            row = r;
            col = c;
        }

        public int compareTo(PathState rhs) {
            return Integer.compare(priority, rhs.priority);
        }
    }

    public int darkestPathPFS(int[][] image, int ur, int uc, int vr, int vc) {
        int rows = image.length, cols = image[0].length;

        PriorityQueue<PathState> pq = new PriorityQueue<PathState>();
        boolean[][] visited = new boolean[rows][cols];

        pq.add(new PathState(image[ur][uc], ur, uc));

        // PFS using maximum value along path as cost
        while (!pq.isEmpty()) {
            PathState s = pq.poll();

            if (s.row == vr && s.col == vc) {
                return s.priority;
            }

            if (visited[s.row][s.col]) continue;
            visited[s.row][s.col] = true;

            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};
            for (int i = 0; i < dr.length; i++) {
                int nr = s.row + dr[i], nc = s.col + dc[i];
                if (nr < 0 || rows <= nr || nc < 0 || cols <= nc) continue;
                if (visited[nr][nc]) continue;
                int priority = Math.max(s.priority, image[nr][nc]); // Compute path brightness
                pq.add(new PathState(priority, nr, nc));
            }
        }

        // This should be unreachable
        return -1;
    }

    public int darkestPathPrims(int[][] image, int ur, int uc, int vr, int vc) {
        int rows = image.length, cols = image[0].length;

        // Construct PQ ordered by pixel brightness
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(
            (lhs, rhs) -> Integer.compare(image[lhs/cols][lhs%cols], image[rhs/cols][rhs%cols]));
        boolean[][] seen = new boolean[rows][cols];

        pq.add(ur * cols + uc);
        seen[ur][uc] = true;
        int result = image[ur][uc];

        while (!pq.isEmpty()) {
            int id = pq.poll();
            int r = id / cols;
            int c = id % cols;

            result = Math.max(result, image[r][c]);

            if (r == vr && c == vc) {
                return result;
            }

            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};
            for (int i = 0; i < dr.length; i++) {
                int nr = r + dr[i], nc = c + dc[i];
                if (nr < 0 || rows <= nr || nc < 0 || cols <= nc) continue;
                if (seen[nr][nc]) continue;
                seen[nr][nc] = true;    // We will never find a better priority for this pixel, so first time we push is fine
                pq.add(nr * cols + nc);
            }
        }

        // This should be unreachable
        return -1;
    }

    /**
     * Compute the results of a list of queries on the given image.
     * Each query will be a three-element int array {r, l, u} defining a row segment. You may assume l < u.
     * A row segment is a set of pixels (r, c) such that r is as defined, l <= c, and c < u.
     * For each query, find the value of the brightest pixel in the specified row segment.
     * Return the query results in the same order as the queries are given.
     *
     * @param image The greyscale image as defined above
     * @param queries The list of query row segments
     * @return The list of brightest pixels for each query row segment
     */
    public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries) {
        // return brightestPixelsNaive(image, queries);
        // return brightestPixelsPrecomp(image, queries);
        // return brightestPixelsSqrtDecomp(image, queries);
        // return brightestPixelsSegmentTree(image, queries);
        return brightestPixelsSparseTable(image, queries);
    }

    public int[] brightestPixelsNaive(int[][] image, int[][] queries) {
        int[] result = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int[] query = queries[q];
            int row = query[0];
            int lwr = query[1];
            int upr = query[2];

            int max = 0;
            for (int i = lwr; i < upr; i++) {
                max = Math.max(max, image[row][i]);
            }

            result[q] = max;
        }

        return result;
    }

    public int[] brightestPixelsPrecomp(int[][] image, int[][] queries) {
        int rows = image.length, cols = image[0].length;

        // Compute solution to all possible queries
        int[][][] precomp = new int[rows][cols][cols+1];
        for (int row = 0; row < rows; row++) {
            for (int lwr = 0; lwr < cols; lwr++) {
                int max = 0;    // Running maximum for queries starting at this lwr
                for (int upr = lwr + 1; upr <= cols; upr++) {
                    max = Math.max(max, image[row][upr-1]);
                    precomp[row][lwr][upr] = max;
                }
            }
        }

        int[] result = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int[] query = queries[q];
            int row = query[0];
            int lwr = query[1];
            int upr = query[2];

            result[q] = precomp[row][lwr][upr];
        }

        return result;
    }


    public int[] brightestPixelsSqrtDecomp(int[][] image, int[][] queries) {
        int rows = image.length, cols = image[0].length;

        int blockWidth = (int) Math.floor(Math.sqrt(cols)); // Size of blocks
        int blockCount = (cols + blockWidth - 1) / blockWidth;  // Number of blocks = ceil(cols/width)

        int[][] blocks = new int[rows][blockCount];
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                int i = col / blockWidth;   // Block this column falls in
                blocks[row][i] = Math.max(blocks[row][i], image[row][col]);
            }
        }

        int[] result = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int[] query = queries[q];
            int row = query[0];
            int lwr = query[1];
            int upr = query[2];

            int max = 0;
            while (lwr < upr && lwr % blockWidth != 0) {
                max = Math.max(max, image[row][lwr++]); // Increase lower bound until block-aligned, keeping running max
            }
            while (lwr < upr && upr % blockWidth != 0) {
                max = Math.max(max, image[row][--upr]); // Decrease upper bound until block-aligned, keeping running max
            }
            for (int i = lwr / blockWidth; i < upr / blockWidth; i++) {
                max = Math.max(max, blocks[row][i]);    // Run over all wholly contained blocks, keeping running max
            }

            result[q] = max;
        }

        return result;
    }

    private int[] buildMaxSegmentTree(int[] vals) {
        int segs = (Integer.highestOneBit(vals.length) << 2) - 1;   // Number of segments in tree, with leaves padded to power of two
        int base = segs >> 1;   // Heap index of first leaf in tree

        int[] tree = new int[segs];

        for (int i = 0; i < vals.length; i++) {
            tree[base + i] = vals[i];   // Copy values into leaves
        }

        for (int i = base - 1; i >= 0; i--) {   // Iterate backwards to work up tree from bottom
            int l = 2 * i + 1, r = 2 * i + 2;   // Left and right child indices
            tree[i] = Math.max(tree[l], tree[r]);   // Max for this segment is max of children
        }

        return tree;
    }

    private int queryMaxSegmentTree(int[] tree, int lwr, int upr) {
        int len = Integer.highestOneBit(tree.length);
        return queryMaxSegmentTree(tree, lwr, upr, 0, 0, len);
    }

    private int queryMaxSegmentTree(int[] tree, int lwr, int upr, int seg, int segLwr, int segUpr) {
        if (lwr <= segLwr && segUpr <= upr) {
            return tree[seg];   // If segment is wholly contained, return value of segment
        }

        if (upr <= segLwr || segUpr <= lwr) {
            return Integer.MIN_VALUE;   // If segment does not overlap, return identity value
        }

        int segMid = (segLwr + segUpr) / 2; // Split point of range
        return Math.max(
            queryMaxSegmentTree(tree, lwr, upr, 2 * seg + 1, segLwr, segMid),   // Query left child
            queryMaxSegmentTree(tree, lwr, upr, 2 * seg + 2, segMid, segUpr));  // Query right child
    }

    public int[] brightestPixelsSegmentTree(int[][] image, int[][] queries) {
        int rows = image.length, cols = image[0].length;

        int[][] segmentTrees = new int[rows][];
        for (int row = 0; row < rows; row++) {
            segmentTrees[row] = buildMaxSegmentTree(image[row]);    // Build segment tree per row
        }

        int[] result = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int[] query = queries[q];
            int row = query[0];
            int lwr = query[1];
            int upr = query[2];

            int[] tree = segmentTrees[row];

            result[q] = queryMaxSegmentTree(tree, lwr, upr);
        }

        return result;
    }

    public int[] brightestPixelsSparseTable(int[][] image, int[][] queries) {
        int rows = image.length, cols = image[0].length;

        // Precompute floor(log_2(i)) for all i <= cols
        int pows = 0;
        int[] logs = new int[cols+1];
        for (int i = 0; i < logs.length; i++) {
            if ((1 << pows) <= i/2) pows++;
            logs[i] = pows;
        }

        // Number of exponential windows
        pows++;

        // Build range maximum query table
        // imageSparseTables[r][p][c] is the maximum value in a window in row r, starting at c, of size 2^p
        int[][][] imageSparseTables = new int[rows][pows][];
        for (int row = 0; row < rows; row++) {
            int[][] sparseTable = imageSparseTables[row];
            sparseTable[0] = Arrays.copyOf(image[row], cols);

            for (int pow = 1; pow < pows; pow++) {
                int[] sparseRow = Arrays.copyOf(sparseTable[pow-1], cols);

                int offset = 1 << (pow - 1);
                for (int col = 0; col + offset < cols; col++) {
                    sparseRow[col] = Math.max(sparseRow[col], sparseRow[col + offset]);
                }

                sparseTable[pow] = sparseRow;
            }
        }

        // Split query windows [lwr, upr) into [lwr, lwr + 2^p) and [upr - 2^p, upr)
        int[] result = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int[] query = queries[q];
            int row = query[0];
            int lwr = query[1];
            int upr = query[2];

            int pow = logs[upr - lwr];
            int[] sparseRow = imageSparseTables[row][pow];

            result[q] = Math.max(sparseRow[lwr], sparseRow[upr - (1 << pow)]);  // Split query range into two overlapping segments
        }

        return result;
    }
}
