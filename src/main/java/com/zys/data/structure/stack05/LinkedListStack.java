package com.zys.data.structure.stack05;

/**
 * 链式栈：基于链表实现的栈
 *
 * @author: zys
 * @date: 2019/10/14 21:57
 */
public class LinkedListStack<T> {
    static class Node<T> {
        // 节点数据
        private T data;

        // 下一个节点
        private Node<T> next;

        Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> head;


    public LinkedListStack() {
        head = new Node<>(null, null);
    }

    /**
     * 入栈
     * 链表 - 头插法
     *
     * @param t
     */
    public void push(T t) {
        head = new Node<>(t, head);
    }

    /**
     * 出栈
     *
     * @return
     */
    public T pop() {
        // 栈空条件判断
        if (head.next == null) {
            return null;
        }
        T result = (T) head.data;
        head = head.next;
        return result;
    }

    public void print() {
        while (head.next != null) {
            System.out.println(pop());
        }
    }

    public static void main(String[] args) {
        LinkedListStack<String> stringLinkedListStack = new LinkedListStack<>();
        stringLinkedListStack.push("1");
        stringLinkedListStack.push("2");
        stringLinkedListStack.push("3");
        stringLinkedListStack.push("4");
        stringLinkedListStack.print();
        System.out.println(stringLinkedListStack.pop());
    }

}
