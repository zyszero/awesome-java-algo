package com.zys.data.structure;

/**
 * @Author: Created by zys
 * @Description: 堆
 * @Date: 2019/5/9 22:25
 * @Modified By:
 */
public class Heap {
    /**
     * 数组，从下标 1 开始存储
     */
    private int[] arrays;


    /**
     * 堆内最大元素个数
     */
    private int n;

    /**
     * 堆内已存储的元素个数
     */
    private int count;

    public Heap(int capacity) {
        this.arrays = new int[capacity + 1];
        this.count = 0;
        this.n = capacity;
    }

    /**
     * 插入元素
     *
     * @param data 插入的元素
     */
    public void add(int data) {
        // 判断堆内数组空间
        if (count >= n) {
            // 堆满了
            return;
        }
        ++count;
        arrays[count] = data;
        // 从下往上堆化
        int i = count;
        while (i / 2 > 0 && arrays[i] > arrays[i / 2]) {
            swap(arrays, i, i / 2);
            i = i / 2;
        }
    }


    /**
     * 移除堆顶元素
     *
     * @return 堆顶元素的值
     */
    public int removeMax() {
        if (count <= 0) {
            return -1;
        }

        int maxValue = arrays[1];

        arrays[1] = arrays[count];
        --count;
        return maxValue;
    }


    /**
     * 从上往下堆化
     *
     * @param arrays    堆内数组
     * @param count     当前堆内元素
     * @param currIndex 当前节点的下标
     */
    private void heapify(int[] arrays, int count, int currIndex) {
        while (true) {
            int maxPos = currIndex;
            // 与左子树节点比较，若小于，设置 maxPos
            if (currIndex * 2 <= count && arrays[currIndex] < arrays[currIndex * 2]) {
                maxPos = currIndex * 2;
            }
            // 将左右子树的节点进行比较，大的设置为 maxPos
            if (currIndex * 2 + 1 <= count && arrays[maxPos] < arrays[currIndex * 2 + 1]) {
                maxPos = currIndex * 2 + 1;
            }
            // 若相等，则证明该下标的节点已是当前子树的最大值
            if (maxPos == currIndex) {
                break;
            }
            // 交换元素
            swap(arrays, currIndex, maxPos);
            currIndex = maxPos;
        }
    }


    /**
     * 交换下标 i 与 下标 i / 2 的值
     *
     * @param arrays      堆内数组
     * @param currIndex   当前节点的下标
     * @param parentIndex 父节点的下标
     */
    private void swap(int[] arrays, int currIndex, int parentIndex) {
        int temp = arrays[currIndex];
        arrays[currIndex] = arrays[parentIndex];
        arrays[parentIndex] = temp;
    }


    /**
     * 建堆
     *
     * @param arrays 堆内数组
     * @param n      数组内元素个数
     */
    private void buildHeap(int[] arrays, int n) {
        // n/2+1 到 n 的节点都是叶子节点，所以 i 从 n / 2 到 下标 1 开始，从上往下进行堆化
        for (int i = n / 2; i > 1; i--) {
            heapify(arrays, n, i);
        }
    }

    /**
     * 堆排序
     *
     * @param arrays 堆内数组
     * @param n      数组内元素个数
     */
    public void sort(int[] arrays, int n) {
        buildHeap(arrays, n);
        int k = n;
        while (k > 1) {
            swap(arrays, 1, k);
            --k;
            heapify(arrays, k, 1);
        }
    }

}
