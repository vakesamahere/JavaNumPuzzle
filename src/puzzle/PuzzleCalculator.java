package puzzle;
import java.util.List;
import java.util.ArrayList;
public class PuzzleCalculator {
    private int[] panel; // 初始状态
    private int blank; // 空格所在位置
    private int size; // 边长
    private int moves; // 当前移动次数
    private int bound; // 当前的深度限制
    private int minMoves;
    private boolean found;
    private List<String> solution;
    private String up="上",down="下",left="左",right="右";
    private Lobby parent;

    public PuzzleCalculator(int[] panelData,Lobby lobby) {
        parent=lobby;
        solution = new ArrayList<>();
        panel = new int[panelData.length];
        for (int i = 0; i < panelData.length; i++) {
            panel[i] = panelData[i];
            if (panel[i] == 0) {
                blank = i;
            }
        }
        size = (int) Math.sqrt(panel.length);//只处理正方形，，
        moves = 0;
        bound = manhattan();
        System.err.println(bound);
        minMoves = -1;
        found = false;
    }

    public int manhattan() {//全局曼哈顿距离
        int distance = 0;
        for (int i = 0; i < panel.length; i++) {
            if (panel[i] != 0) {//skip air
                int x = i % size;//当前列
                int y = i / size;//当前行
                int targetX = (panel[i] - 1) % size;//目标列
                int targetY = (panel[i] - 1) / size;//目标行
                distance += Math.abs(x - targetX) + Math.abs(y - targetY);
            }
        }
        return distance;
    }

    public void swap(int pos) {//move
        int temp = panel[pos];
        panel[pos] = panel[blank];
        panel[blank] = temp;
        blank = pos;
    }

    public int search(int last) {//last传入上一次移动0123上下左右，-1代表初始
        if (manhattan() == 0) { //已解
            minMoves = moves;
            found = true;
            return 0;//结束递归
        }
        if (manhattan() + moves > bound) {//剪枝
            return manhattan() + moves;
        }
        int min = Integer.MAX_VALUE;
        //动
        if (last != 1 && blank >= size) { //上
            solution.add(up);
            swap(blank - size);//动
            moves++;//计步
            int result = search(0);
            if (result == 0) {
                return 0;
            }
            //这条路没最优解
            min = Math.min(min, result); 
            moves--; //取消本次计步
            swap(blank + size); //取消移动
            solution.remove(solution.size()-1);
        }
        if (last != 0 && blank < panel.length - size) { //下
            solution.add(down);
            swap(blank + size);
            moves++;
            int result = search(1);
            if (result == 0) {
                return 0;
            }
            min = Math.min(min, result);
            moves--;
            swap(blank - size);
            solution.remove(solution.size()-1);
        }
        if (last != 3 && blank % size > 0) { //左
            solution.add(left);
            swap(blank - 1);
            moves++;
            int result = search(2);
            if (result == 0) {
                return 0;
            }
            min = Math.min(min, result); //
            moves--;
            swap(blank + 1);
            solution.remove(solution.size()-1);
        }
        if (last != 2 && blank % size < size - 1) { //右
            solution.add(right);
            swap(blank + 1);
            moves++;
            int result = search(3);
            if (result == 0) {
                return 0;
            }
            min = Math.min(min, result);
            moves--;
            swap(blank - 1);
            solution.remove(solution.size()-1);
        } return min;
    }

    public int solve() {
        while (!found && bound <= 100) { //最深100
            int result = search(-1);
            if (result == 0) {
                System.err.println(minMoves);
                String text=String.join(",", solution);
                System.err.println(text);
                parent.getScoreBoard().getCheat().setText(text);
                return minMoves;
            }
            bound = result;
        }
        System.err.println("none");
        return -1;
    }

}