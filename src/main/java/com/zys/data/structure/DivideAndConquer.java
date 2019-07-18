package com.zys.data.structure;

/**
 * 分治算法
 *
 * @author: zys
 * @date: 2019/7/18 22:44
 */
public class DivideAndConquer {

    /**
     * 有序对个数或者逆序对个数
     */
    private int num = 0;

    /**
     * 1. 如何编程求出一组数据的有序对个数或者逆序对个数呢？
     *
     * @param a 一组数据
     * @return
     */
    public int count(int[] a) {
        num = 0;
        mergeAndSort(a, 0, a.length - 1);
        return num;
    }

    private void mergeAndSort(int[] a, int start, int end) {
        // 边界条件
        if (start >= end) {
            return;
        }
        int mid = (start + end) / 2;
        mergeAndSort(a, start, mid);
        mergeAndSort(a, mid + 1, end);
        merge(a, start, mid, end);
    }

    /**
     * 画下图，会更好的理解
     *
     * @param a
     * @param start
     * @param mid
     * @param end
     */
    private void merge(int[] a, int start, int mid, int end) {
        int i = start, j = mid + 1, k = 0;
        int[] tmp = new int[end - start + 1];
        while (i <= mid && j <= end) {
            if (a[i] <= a[j]) {
                tmp[k++] = a[i++];
            } else {
                // 统计 start - end 的区间，比 a[j] 大的元素个数
                num++;
                tmp[k++] = a[j++];
            }
        }
        // 将剩余的元素填充到数组 tmp 中
        while (i <= mid) {
            tmp[k++] = a[i++];
        }
        while (j <= end) {
            tmp[k++] = a[j++];
        }

        if (tmp.length >= 0) {
            // 从数组 tmp copy 回数组 a
            System.arraycopy(tmp, 0, a, start, tmp.length);
        }

    }


}
