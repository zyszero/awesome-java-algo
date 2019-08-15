package com.zys.data.structure.recall;

/**
 * 0-1 Backpack question
 *
 * @author: zys
 * @date: 2019/7/23 22:56
 */
public class ZeroOrOneBackpack {

    private int maxWeight = 0;

    /**
     * 我们有一个背包，背包总的承载重星是Wkg。现在我们有n个物品，每个物品的重量不等，并且不可分割。
     * 我们现在期望选择几件物品，装载到背包中。在不超过背包所能装载重量的前提下，如何让背包中物品的总重量最大？
     * 回溯算法
     *
     * @param w     背包总的承载重量是Wkg
     * @param n     n个物品
     * @param cw    当前已经装进去的物品重量总和
     * @param i     表示考察到哪个物品
     * @param items 表示每个物品的重量
     */
    public void backPack(int w, int n, int cw, int i, int[] items) {
        // 当物品考察完毕 或 当前已经装进去的物品重量总和达到背包总的承载重量(终止条件)
        if (i == n || cw == w) {
            if (cw > maxWeight) {
                maxWeight = cw;
            }
            return;
        }
        // 大问题分解为小问题
        backPack(w, n, cw, i + 1, items);
        // 超过背包总的承载重量时，就不再装载物品了
        if (cw + items[i] <= w) {
            // 加入物品
            backPack(w, n, i + 1, cw + items[i], items);
        }
    }

    /**
     *
     */
    private int maxW = Integer.MIN_VALUE;
    /**
     * 物品重量
     */
    private int[] items = new int[]{2, 2, 4, 6, 4};
    /**
     * 物品个数
     */
    private int n = 5;

    /**
     * 最大承重量
     */
    private int w = 9;

    /**
     * 备忘录
     */
    private boolean[][] men = new boolean[5][10];

    /**
     *
     * 使用“备忘录”，可以对回溯算法进行优化
     *
     * @param i
     * @param cw
     */
    public void backpack(int i, int cw) {
        // 当物品考察完毕 或 当前已经装进去的物品重量总和达到背包总的承载重量(终止条件)
        if (i == n || cw == w) {
            if (cw > maxWeight) {
                maxW = cw;
            }
            return;
        }
        // 符合条件就跳过，表明当前是重复计算
        if (men[i][cw]) {
            return;
        }
        // 记录f(i, cw)这个状态
        men[i][cw] = true;
        // 大问题分解为小问题
        // 不加入背包
        backpack(i + 1, cw);
        if (cw + items[i] < w) {
            backpack(i + 1, cw + items[i]);
        }
    }


}
