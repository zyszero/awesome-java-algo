package com.zys.data.structure;

import java.util.Arrays;

/**
 * @decription: BM 字符串匹配算法
 * @author: zys
 * @date: 2019/6/3 21:19
 */
public class BM {
    /**
     * 全局变量
     */
    private static final int SIZE = 256;

    /**
     * 构建坏字符的散列表
     *
     * @param patternStr 模式串
     * @param m          模式串的长度
     * @param hash       散列表，这里简单实现一个散列表，假设字符串的字符集不是很大，每个字符长度是1字节，
     *                   用大小为256的数组，来记录每个字符在模式串中出现的位置。
     *                   数组的下标对应字符的ASCIl码值，数组中存储这个字符在模式串中出现的位置。
     */
    private void generateHash(char[] patternStr, int m, int[] hash) {
        // 初始化散列表
        Arrays.fill(hash, -1);
        for (int i = 0; i < m; i++) {
            // 字符转成 int 就是 ASCII 值了
            int ascii = (int) patternStr[i];
            // 数组中存储这个字符在模式串中出现的位置
            hash[ascii] = i;
        }
    }

    /**
     * 构建好后缀的 suffix 数组和 prefix 数组
     *
     * @param patternStr 模式串
     * @param m          模式串长度
     * @param suffix     数组的下标，表示后缀子串的长度，下标对应的值是模式串中跟好后缀 {u} 相匹配的子串 {u*}的起始下标
     * @param prefix     记录模式串的后缀子串是否能匹配模式串中的前缀子串，下标为公共后缀子串的长度
     */
    private void generateGS(char[] patternStr, int m, int[] suffix, boolean[] prefix) {
        // 初始化 suffix 和 prefix
        for (int i = 0; i < m; i++) {
            suffix[i] = -1;
            prefix[i] = false;
        }

        // m 是模式串的长度，所以 m-1 是模式串的末尾下标
        for (int i = 0; i < m - 1; i++) {
            int j = i;
            // 公共后缀子串的长度 k
            int k = 0;
            // 与 patternStr[0, i] 求公共后缀子串
            while (j >= 0 && patternStr[j] == patternStr[m - 1 - k]) {
                --j;
                ++k;
                // j+1 表示公共后缀子串在 patternStr[0, i] 中的起始下标，j+1的原因是，前面先进行了 --j 的操作
                suffix[k] = j + 1;
            }
            if (j == -1) {
                //表明公共后缀子串也是模式串的前缀子串，比如cabcab，前缀子串是 cab，后缀子串是cab
                prefix[k] = true;
            }
        }
    }

    /**
     * 利用好后缀原则，计算模式串需要往后移动的位数
     *
     * @param j      坏字符对应模式串中的下标
     * @param m      模式串的长度
     * @param suffix
     * @param prefix
     * @return
     */
    private int moveByGS(int j, int m, int[] suffix, boolean[] prefix) {
        // 好后缀的长度
        int k = m - 1 - j;
        // 如果找到另一个相匹配的子串 {u*}，将模式串滑动到子串 {u*} 与 主串中 {u} 对齐的位置
        if (suffix[k] != -1) {
            return j - suffix[k] + 1;
        }
        // r = j + 2，是因为 数组 prexfix 的下标是后缀子串的长度，所以 r = j + 2，其实是 0 到下标 j + 1的长度
        for (int r = j + 2; r < m - 1; ++r) {
            // m -r 是后缀子串的长度 即 k = m -r
            if (prefix[m - r]) {
                return r;
            }
        }
        // 证明公共后缀子串不是模式串的前缀子串
        return m;
    }


    /**
     * BM 算法
     *
     * @param primaryArr 主串
     * @param n          主串长度
     * @param patternArr 模式串
     * @param m          模式串长度
     * @return 匹配成功，返回主串与模式串第一个匹配的字符的位置；匹配失败，返回 -1。
     */
    public int bm(char[] primaryArr, int n, char[] patternArr, int m) {
        // 创建散列表
        int[] hash = new int[SIZE];
        // 构建坏字符的散列表
        generateHash(patternArr, m, hash);
        int[] suffix = new int[m];
        boolean[] prefix = new boolean[m];
        // 构建好后缀的 suffix 数组 和 prefix 数组
        generateGS(patternArr, m, suffix, prefix);
        // i 表示主串与模式串对齐的第一个字符
        int i = 0;
        //直至模式串移至主串尾部
        while (i < n - m) {
            // j 表示坏字符对应模式串中的下标
            int j;
            // 模式串从后外前匹配
            for (j = m - 1; j >= 0; j--) {
                // 找到坏字符
                if (primaryArr[i + j] != patternArr[j]) {
                    break;
                }
            }
            if (j < 0) {
                // 匹配成功，返回主串与模式串第一个匹配的字符的位置
                return i;
            }
            // 存在坏字符，i+j 是坏字符在主串中的下标，primaryArr[i+j] 是坏字符，
            // hash[(int) primaryArr[i + j]] 是模式串中与坏字符相等的字符的下标
            // j - hash[(int) primaryArr[i + j]] 则是模式串需要往后移动的位数，这样模式串中的“坏字符”才能和主串中的坏字符对齐
            int x = j - hash[(int) primaryArr[i + j]];
            int y = 0;
            // 判断是否存在好后缀，只要坏字符对应模式串中的下标不是模式串的末尾，就证明存在好后缀
            if (j < m - 1) {
                y = moveByGS(j, m, suffix, prefix);
            }
            // 比较坏字符原则和好后缀原则计算得到的位数，取最大数，作为滑动位数，这样还可以避免采用坏字符规则计算滑动位数，可能出现负数的情况。
            i = i + Math.max(x, y);
        }
        return -1;
    }
}
