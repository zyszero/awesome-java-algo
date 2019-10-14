package com.zys.data.structure.stack05;

import java.util.Arrays;

/**
 * 顺序栈：FILO
 * 支持动态扩容的顺序栈
 *
 * @author: zys
 * @date: 2019/10/14 21:10
 */
public class ArrayStack<T> {
    /**
     * 栈中元素
     */
    private int count;

    /**
     * 存储数据的数据
     */
    private T[] items;

    /**
     * 栈大小
     */
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayStack(int size) {
        this.size = size;
        this.items = (T[]) new Object[size];
    }

    /**
     * 入栈
     *
     * @param t
     * @return boolean
     */
    public boolean push(T t) {
        // 判断栈是否已满
        if (count == size) {
            // 栈满，自动扩容
            size = size * 2;
            items = Arrays.copyOf(items, size);
        }
        items[count] = t;
        count++;
        return true;
    }

    /**
     * 出栈
     *
     * @return
     */
    public T pop() {
        if (count == 0) {
            return null;
        }
        return (T) items[--count];
    }

    public void printf() {
        for (int i = count - 1; i >= 0; i--) {
            System.out.println(pop());
        }
    }

    public static void main(String[] args) {
        ArrayStack<String> stringArrayStack = new ArrayStack<>(4);
        stringArrayStack.push("1");
        stringArrayStack.push("2");
        stringArrayStack.push("3");
        stringArrayStack.push("4");
        stringArrayStack.printf();
        stringArrayStack.push("1");
        stringArrayStack.push("2");
        stringArrayStack.push("3");
        stringArrayStack.push("4");
        stringArrayStack.push("5");
        stringArrayStack.printf();
    }
}
