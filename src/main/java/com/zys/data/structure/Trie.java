package com.zys.data.structure;

import java.util.*;

/**
 * Trie树实现代码
 * 假设字符集是 a-z 26个字母，利用ASCII值来计算下标index
 *
 * @author: zys
 * @date: 2019/6/16 15:01
 */
public class Trie {
    /**
     * Trie树的根节点
     */
    private TrieNode root;

    /**
     * 字符集大小
     */
    private int size;

    class TrieNode {
        private char data;
        /**
         * 用对象数组的方式代替指针
         */
        private TrieNode[] children;
        /**
         * 判断是否是结尾字符
         */
        private boolean isEndingChar = false;

        TrieNode(char data, TrieNode[] children) {
            this.data = data;
            this.children = children;
        }

    }

    public Trie(int size) {
        this.size = size;
        this.root = new TrieNode('/', new TrieNode[size]);
    }

    /**
     * 往Trie树插入字符串
     *
     * @param chars 字符串
     */
    public void insert(char[] chars) {
        TrieNode p = root;
        for (char c : chars) {
            int index = c - 'a';
            if (p.children[index] == null) {
                p.children[index] = new TrieNode(c, new TrieNode[size]);
            }
            p = p.children[index];
        }
        // 结尾字符设置标识符
        p.isEndingChar = true;
    }


    /**
     * 查找有无匹配的字符串
     *
     * @param pattern 要匹配的字符串
     * @return
     */
    public boolean find(char[] pattern) {
        TrieNode p = root;
        for (char c : pattern) {
            int index = c - 'a';
            if (p.children[index] == null) {
                // 不存在
                return false;
            }
            p = p.children[index];
        }
        return p.isEndingChar;
    }

    /**
     * 自动补全
     *
     * @param pattern 输入的字符串
     * @return
     */
    public List<String> autoCompletion(char[] pattern) {
        List<String> result = null;
        TrieNode p = root;
        for (char c : pattern) {
            int index = c - 'a';
            if (p.children[index] == null && !p.isEndingChar) {
                return null;
            }
            p = p.children[index];
        }
        if (!p.isEndingChar) {
            StringBuilder prev = new StringBuilder();
            for (char c : pattern) {
                prev.append(c);
            }
            result = new ArrayList<>();
            getAutoCompletions(result, p, prev.toString());
        }
        return result;
    }

    private void getAutoCompletions(List<String> list, TrieNode trieNode, String prev) {
        if (trieNode != null) {
            if (trieNode.isEndingChar) {
                list.add(prev);
            }
            if (trieNode.children != null) {
                for (TrieNode child : trieNode.children) {
                    if (child != null) {
                        prev += child.data;
                        getAutoCompletions(list, child, prev);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        Trie trie = new Trie(26);
        trie.insert("hello".toCharArray());
        trie.insert("hell".toCharArray());
        trie.insert("hel".toCharArray());
//        trie.insert("he".toCharArray());
        trie.insert("hellword".toCharArray());
        trie.autoCompletion("he".toCharArray()).forEach(System.out::println);
    }
}
