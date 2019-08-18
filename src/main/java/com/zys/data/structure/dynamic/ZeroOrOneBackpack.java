package com.zys.data.structure.dynamic;

/**
 * 利用动态规划来求解 0 - 1 背包问题
 *
 * @author: zys
 * @date: 2019/8/13 23:30
 */
public class ZeroOrOneBackpack {

    /**
     * 0 - 1 背包问题
     *
     * @param weight 物品重量
     * @param w      背包的最大承重
     */
    public int backpack1(int[] weight, int w) {
        // 物品个数
        int n = weight.length;
        // 状态数组，默认是false
        boolean[][] states = new boolean[n][w + 1];
        // 哨兵优化
        states[0][0] = true;
        if (weight[0] < w) {
            states[0][weight[0]] = true;
        }
        for (int i = 1; i < n; i++) {
            // 动态规划，状态转移
            for (int j = 0; j <= w; j++) {
                // 不选择把物品放入背包
                if (states[i - 1][j]) {
                    states[i][j] = states[i - 1][j];
                }
            }
            for (int j = 0; j <= w - weight[i]; j++) {
                // 选择把物品放入背包
                if (states[i - 1][j]) {
                    states[i][weight[i] + j] = true;
                }
            }
        }

        for (int i = w; i >= 0; i--) {
            if (states[n - 1][i]) {
                return i;
            }
        }
        return 0;
    }

    /**
     * 0 - 1 背包问题，动态规划优化空间复杂度
     * 核心：去除不累加物品的状态分支
     * 可以通过调试代码，在重新理一下思路
     *
     * @param weight
     * @param w
     * @return
     */
    public int backpack(int[] weight, int w) {
        // 状态数组
        boolean[] states = new boolean[w + 1];
        // 物品个数
        int n = weight.length;
        // 哨兵优化
        if (weight[0] <= w) {
            states[weight[0]] = true;
        }
        // i 为考察个数
        for (int i = 1; i < n; ++i) {
            // 从大到小的原因是为了避免重复计算
            for (int j = w - weight[i]; j >= 0; j--) {
                // 选择把第 i 个物品放入背包
                // 提示： 基于上一个状态来看
                // 核心思想：就是状态分支
                if (states[j]) {
                    states[weight[i] + j] = true;
                }
            }
        }
        for (int i = w; i >= 0; --i) {
            if (states[i]) {
                return i;
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        new ZeroOrOneBackpack().backpack(new int[]{2, 2, 4, 6, 3}, 9);
    }
}
