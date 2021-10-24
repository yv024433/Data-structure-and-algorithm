import java.util.*;

/**
 * @author Andrew "Gozz" Gozzard <andrew.gozzard@uwa.edu.au>
 *
 * Reference implementation of 2020 CITS2200 project.
 */
public class ReferenceProject implements Project {
    public int floodFillCount(int[][] image, int row, int col) {
        int initial_colour = image[row][col];

        int rows = image.length, cols = image[0].length;

        Queue<Integer> queue = new ArrayDeque<Integer>();
        boolean[][] seen = new boolean[rows][cols];

        if (initial_colour != 0) {
            queue.add(row * cols + col);    // Convert coordinates to unique id
            seen[row][col] = true;
        }

        int result = 0;

        while (!queue.isEmpty()) {
            int id = queue.poll();
            int r = id / cols;
            int c = id % cols;

            result++;

            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};
            for (int i = 0; i < dr.length; i++) {
                int nr = r + dr[i], nc = c + dc[i];
                if (nr < 0 || rows <= nr || nc < 0 || cols <= nc) continue;
                if (seen[nr][nc] || image[nr][nc] != initial_colour) continue;
                seen[nr][nc] = true;
                queue.add(nr * cols + nc);
            }
        }

        return result;
    }

    public int brightestSquare(int[][] image, int k) {
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

    public int darkestPath(int[][] image, int ur, int uc, int vr, int vc) {
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
                seen[nr][nc] = true;
                pq.add(nr * cols + nc);
            }
        }

        // This should be unreachable
        return -1;
    }

    public int[] brightestPixelsInRowSegments(int[][] image, int[][] queries) {
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
