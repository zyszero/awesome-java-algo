package com.zys.data.structure;

import java.util.Arrays;

/**
 * @decription: KPM 算法
 * @author: zys
 * @date: 2019/6/8 12:29
 */
public class KPM {

    /**
     * 求部分匹配表（PMT），或者叫做失效函数。
     * 这里的 PMT 数组存放的是 字符串前缀子串和后缀子串的交集中最长元素的长度
     * 为了方便编程，在这里将 pmt 数组整体往后移动一位，假设为 next 数组，next[0] = -1，不代表什么含义。
     * 具体思路可看：https://www.zhihu.com/question/21923021/answer/281346746
     * 结合图形会更好的理解
     *
     * @param b 模式串
     * @param m 模式串的长度
     * @return 返回 PMT 的数组
     */
    public static int[] getNexts1(char[] b, int m) {
        int[] next = new int[m];
        next[0] = -1;
        int i = 0, k = -1;
        while (i < m - 1) {
            if (k == -1 || b[i] == b[k]) {
                ++i;
                ++k;
                next[i] = k;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    /**
     * 求部分匹配表（PMT），或者叫做失效函数。在这里假设为 next 数组
     * 在这里，next 数组的下标是每个前缀结尾字符下标，next 数组的值是这个前缀的最长可以匹配前缀子串的结尾字符下标
     * 如果不存在最长可匹配的前缀子串，next 数组对应下标的值为 -1
     * <p>
     * PMT 的本质是：求出模式串中每种前缀子串的最长可匹配前缀子串
     * 对于模式串的每种前缀子串，依次取出前缀子串的所有后缀子串，然后逐个对比，找出最长可匹配前缀子串，可以形成 PMT 数组，但这样的效率太低
     * <p>
     * 那如何快速求出 PMT 数组呢？
     * 核心思维是：
     * 假设字符串的 b[0, i-1] 的最长可匹配前缀子串是 b[0, k-1]，即 next[i-1] = k-1; 那么当 b[k] = b[i]时，b[0, k] 是 b[0, i]的最长可匹配前缀子串 即 next[i] = k
     * <p>
     * 若 b[k] != b[i] 的时候，假设字符串 b[0, i] 的最长可匹配后缀子串是 b[r, i]，那么 b[r, i-1] 肯定是 b[0, i-1] 的可匹配后缀子串，但不一定是最长可匹配后缀子串。
     * 这时我们比较 b[0, i-1] 的次长可匹配后缀子串 b[x, i-1] 对应的次长可匹配前缀子串 b[0, i-1-x] 的下一个字符 b[i-x] 是否匹配 b[i]，
     * 如果等于，那b[x, i] 就是 b[0, i]的最长可匹配后缀子串
     * <p>
     * 那如果求得 b[0, i-1] 的次长可匹配后缀子串呢？
     * 次长可匹配后缀子串肯定被包含在最长可匹配后缀子串中，而最长可匹配后缀子串又对应最长可匹配前缀子串 b[0，y]。
     * 于是，查找 b[0，i-1] 的次长可匹配后缀子串，这个问题就变成，查找 b[0，y] 的最长匹配后缀子串的问题了。
     * 按照这个思路，我们可以考察完所有的 b[0，i-1] 的可匹配后缀子串 b[y，i-1]，
     * 直到找到一个可匹配的后缀子串，它对应的前缀子串的下一个字符等于 b[i]，那这个 b[y，i] 就是 b[0，i]的最长可匹配后缀子串。
     *
     * @param b 模式串
     * @param m 模式串的长度
     * @return 返回 PMT 的数组
     */
    public static int[] getNexts(char[] b, int m) {
        //初始化 next 数组
        int[] next = new int[m];
        next[0] = -1;
        //假设 k 是最长可匹配前缀子串的结尾下标， -1 表示不存在
        int k = -1;
        // 这里的 i 表示模式串的每种前缀子串的尾下标
        for (int i = 1; i < m; i++) {
            while (k != -1 && b[k + 1] != b[i]) {
                // 因为前一个的最长可匹配前缀字符串的下一个字符不与最后一个字符相等，需要找前一个的次长串，问题就变成了求 0 到 next[k] 的最长可匹配前缀字符串，
                // 如果它的下个字符与最后一个不等，继续求次长串，也就是下一个next[k]，直到找到，或者完全没有。
                k = next[k];
            }
            if (b[k + 1] == b[i]) {
                ++k;
            }
            next[i] = k;
        }
        return next;
    }

    /**
     * KMP 字符串匹配算法
     *
     * @param a 主串
     * @param n 主串的长度
     * @param b 模式串
     * @param m 模式串的长度
     * @return 匹配成功，返回主串与模式串第一个匹配的字符的位置；匹配失败，返回 -1。
     */
    public int kmp1(char[] a, int n, char[] b, int m) {
        // j 是坏字符在模式串中对应的下标
        int i = 0, j = 0;
        int[] next = getNexts1(b, m);
        while (i < n && j < m) {
            if (j == -1 || a[i] == b[j]) {
                i++;
                j++;
            } else {
                // 当a[i] != b[j] 时，即找到坏字符，
                j = next[j];
            }
        }
        if (j == m) {
            return i - j;
        }
        return -1;
    }


    /**
     * KMP 字符串匹配算法
     *
     * @param a 主串
     * @param n 主串的长度
     * @param b 模式串
     * @param m 模式串的长度
     * @return 匹配成功，返回主串与模式串第一个匹配的字符的位置；匹配失败，返回 -1。
     */
    public int kmp(char[] a, int n, char[] b, int m) {
        // j 代表坏字符在模式串中对应的下标
        int j = 0;
        int[] next = getNexts(b, m);
        for (int i = 0; i < n; i++) {
            // 当b[j] != a[i]时，j 即为坏字符在模式串中对应的下标
            while (j > 0 && b[j] != a[i]) {
                // next[j - 1] 即为好前缀
                j = next[j - 1] + 1;
            }
            if (a[i] == b[j]) {
                // ++j 有两种含义，一种是看成移动位置的字符串的长度，另一种是坐标往后挪动一位
                ++j;
            }
            // 当 j == m 时，证明找到匹配模式串了
            if (j == m) {
                return i - m + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(getNexts(new char[]{'a', 'b', 'a', 'b', 'a', 'b', 'c', 'a'}, 8)));
    }
}
