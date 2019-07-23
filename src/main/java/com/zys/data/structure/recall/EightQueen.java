package com.zys.data.structure.recall;

/**
 * eight queen question
 * <p>
 * 我们有一个8×8的棋盘，希望往里放8个棋子（皇后），每个棋子所在的行、列、对角线都不能有另一个棋子。找出所有满足这种要求的放棋子方式。
 *
 * @author: zys
 * @date: 2019/7/23 21:49
 */
public class EightQueen {
    /**
     * 用于存放八皇后的结果，下标表示行，值表示 queen 存放在哪一列
     */
    private int[] result = new int[8];

    /**
     * 调用八皇后
     * 递归解题三步骤
     *
     * @param row
     */
    public void callEightQueen(int row) {
        if (row == result.length) {
            // 8 个棋子已经排列好了，打印棋子
            printfQueens(result);
            return;
        }
        for (int col = 0; col < result.length; col++) {
            if (isOk(row, col)) {
                result[row] = col;
                // 考察下一行
                callEightQueen(row + 1);
            }
        }
    }

    private boolean isOk(int row, int col) {
        int leftCol = col - 1, rightCol = col + 1;
        while (row > 0) {
            if (result[row - 1] == col) {
                return false;
            }
            // 只有当 leftCol >= 0 时，左上角线上才有可能放棋子
            if (leftCol >= 0) {
                if (result[row - 1] == leftCol) {
                    return false;
                }
            }
            // 只有当 rightCol < 8 时，右上角线上才有可能放棋子
            if (rightCol < 8) {
                if (result[row - 1] == rightCol) {
                    return false;
                }
            }
            // 往上查看
            row--;
            leftCol--;
            rightCol++;

        }
        return true;
    }

    /**
     * 打印八皇后的排列方式
     *
     * @param result
     */
    private void printfQueens(int[] result) {
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result.length; col++) {
                if (result[row] == col) {
                    System.out.print("Q ");
                } else {
                    System.out.print("* ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        EightQueen eightQueen = new EightQueen();
        eightQueen.callEightQueen(0);
    }
}
