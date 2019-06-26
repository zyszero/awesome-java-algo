package com.zys.data.structure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * AC
 *
 * @author: zys
 * @date: 2019/6/26 23:35
 */
public class AC {
    /**
     * Trie 树的根节点
     */
    private AcTrieNode root;

    /**
     * 模式串集合的字符集范围大小
     */
    private int size;

    public class AcTrieNode {
        /**
         * 存放字符
         */
        private char data;
        /**
         * 当 isEndingChar = true 时，记录字符串长度
         */
        private int length = -1;

        /**
         * 用对象数组的方式代替指针
         */
        private AcTrieNode[] children;
        /**
         * 判断是否是结尾字符
         */
        private boolean isEndingChar = false;

        /**
         * 失败指针，核心部分，本质：失败指针指向的节点到root之间的字符串是可匹配后缀子串，类似于 KMP 算法的失效函数（部分匹配数组）next 数组
         * <p>
         * 字符串abc的后缀子串有两个bc，c，我们拿它们与其他模式串匹配，如果某个后缀子串可以匹配某个模式串的前缀，那我们就把这个后缀子串叫作可匹配后缀子串。
         */
        private AcTrieNode fail;

        public AcTrieNode(char data, AcTrieNode[] acTrieNodes) {
            this.data = data;
            this.children = acTrieNodes;
        }
    }

    public AC(int size) {
        this.size = size;
        //根节点的值为 '/' ，无任何意义
        this.root = new AcTrieNode('/', new AcTrieNode[size]);
    }

    /**
     * 往 Trie 树插入模式串
     *
     * @param chars 模式串
     */
    public void insert(char[] chars) {
        AcTrieNode p = this.root;
        int length = 0;
        for (char aChar : chars) {
            int idx = aChar - 'a';
            if (p.children[idx] == null) {
                p.children[idx] = new AcTrieNode(aChar, new AcTrieNode[size]);
                length++;
            }
            p = p.children[idx];
        }
        p.isEndingChar = true;
        p.length = length;
    }

    /**
     * 构造失败指针（本质是：找出所有可匹配后缀子串）
     * 核心思路：
     * 假设节点 p 的失败指针指向节点 q：
     * 1. 那么当节点 q 的子节点 qc 包含节点 p 的子节点 pc 的字符，则节点 pc 的失败指针可以直接指向节点 qc
     * 2. 若不包含，则继续往上一层查找，即让 q = q->fail，继续 1,2 点的判断，直至节点 q 为节点 root
     */
    public void buildFailurePointer() {
        Queue<AcTrieNode> queue = new LinkedList<>();
        root.fail = null;
        // 先把根节点先入队
        queue.add(root);
        while (!queue.isEmpty()) {
            // 取出头部元素
            AcTrieNode p = queue.remove();
            for (int i = 0; i < size; i++) {
                AcTrieNode pc = p.children[i];
                // 如果为null，证明不存在，直接跳过这个字符
                if (pc == null) {
                    continue;
                }
                // 当 p 指向根节点时，相当于上层节点只有根节点，所以下级节点的失败指针指向 root
                if (p == root) {
                    pc.fail = root;
                } else {
                    AcTrieNode q = p.fail;
                    // 直至 q 指针指向 root 节点
                    while (q != null) {
                        // 查找 p 节点的失败指针指向的节点的子节点 qc 是否包含与 p节点的子节点 pc 的字符
                        AcTrieNode qc = q.children[pc.data - 'a'];
                        // 当不为空时，即包含
                        if (qc != null) {
                            pc.fail = qc;
                            break;
                        }
                        q = q.fail;
                    }
                    // 使用if的原因是 while 循环里，存在 break 的情况。
                    // q 指针一直向上找失败指针，直至指向了没有失败指针的节点，即指向了root，所以 pc 节点的失败指针指向 root
                    if (q == null) {
                        pc.fail = root;
                    }
                }
                // 将每个节点入队
                queue.add(pc);
            }
        }
    }

    /**
     * AC 自动机匹配方法（本质就是：找出最长可匹配后缀子串）
     * 核心思路：
     * 在匹配过程中，主串从 i = 0 开始，AC 自动机从指针 p = root 开始，假设模式串是 b，主串是 a。
     * 1. 如果 p 指向的节点有一个等于 b[i] 的子节点 x，就更新 p 指向 x，然后通过失败指针，检测一系列以失败指针为结尾的路径是否是模式串。处理完之后，将 i 加一，继续 1、2；
     * 2. 如果 p 指向的节点没有等于 b[i] 的子节点，那么就将 p = p->fail ，继续 1、2。
     *
     * @param text 用户输入的文本（主串）
     */
    public void match(char[] text) {
        int n = text.length;
        AcTrieNode p = root;
        for (int i = 0; i < n; i++) {
            int idx = text[i] - 'a';
            // 相当于步骤 2
            // p.children[idx] == null 证明当前节点 p 的子节点的值没有匹配到 c，
            // && p != root 这个是终止条件，当失败指针找不到符合的值时，需要继续往上层寻找，直至节点 p 为 节点 root
            while (p.children[idx] == null && p != root) {
                p = p.fail;
            }
            // 步骤 1
            p = p.children[idx];
            if (p == null) {
                // 如果没有匹配的，从root重新开始匹配
                p = root;
            }
            AcTrieNode tmp = p;
            // 排除没有匹配到的情况，通过失败指针，检测一系列以失败指针为结尾的路径是否是模式串
            while (tmp != root) {
                if (tmp.isEndingChar) {
                    int pos = i - tmp.length + 1;
                    System.out.println("匹配下标：" + pos + "；长度：" + tmp.length);
                }
                // 证明不是完全匹配
                tmp = tmp.fail;
            }
        }
    }

}
