package com.ximi.wendashequ.util;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 单广美 on 2017/10/18.
 *
 * @Description:
 */
@Service
public class SensitiveService implements InitializingBean {

    private final static Logger log = LoggerFactory.getLogger(SensitiveService.class);

    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("sensitive.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String lineText;
            while((lineText = bufferedReader.readLine()) != null){
                lineText = lineText.trim();//去掉尾部的空格
                addWord(lineText);
            }
            bufferedReader.close();
        }catch (Exception e){
            log.error("读取敏感词文件失败",e.getMessage());
        }
    }

    private class TreeNode {
        /*是一个关键词结束标志*/
        private boolean end = false;
        /*节点*/
        private Map<Character,TreeNode> subNodes = new HashMap<>();

        /*添加节点*/
        public void addNode(Character key,TreeNode node){
            subNodes.put(key,node);
        }
        /*获取节点*/
        public TreeNode getSubNodes(Character key){
            return subNodes.get(key);
        }
        public boolean isEnd(){
            return end;
        }

        public void setEnd(boolean end) {
            this.end = end;
        }
    }
    /*根节点*/
    private TreeNode rootNode = new TreeNode();
    /*定义替换敏感词的常量*/
    private static final  String DEFAULT_REPLACEMENT = "***";

    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    public void addWord(String lineText){
        TreeNode tempNode = rootNode;
        for (int i = 0;i<lineText.length();++i){
            Character character = lineText.charAt(i);
            TreeNode node = tempNode.getSubNodes(character);
            if (node == null){
                node = new TreeNode();
                tempNode.addNode(character,node);
            }
            tempNode = node;

            if (i == lineText.length() - 1) {
                // 关键词结束， 设置结束标志
                tempNode.setEnd(true);
            }
        }

    }
    /**
     * 过滤敏感词
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String replacement = DEFAULT_REPLACEMENT;
        //结果
        StringBuilder result = new StringBuilder();

        TreeNode tempNode = rootNode;

        int begin = 0; // 回滚数
        int position = 0; // 当前比较的位置

        while (position < text.length()) {
            char c = text.charAt(position);
            // 空格直接跳过
            if (isSymbol(c)) {
                if (tempNode == rootNode) {
                    result.append(c);
                    ++begin;
                }
                ++position;
                continue;
            }

            tempNode = tempNode.getSubNodes(c);

            // 当前位置的匹配结束
            if (tempNode == null) {
                // 以begin开始的字符串不存在敏感词
                result.append(text.charAt(begin));
                // 跳到下一个字符开始测试
                position = begin + 1;
                begin = position;
                // 回到树初始节点
                tempNode = rootNode;
            } else if (tempNode.isEnd()) {
                // 发现敏感词， 从begin到position的位置用replacement替换掉
                result.append(replacement);
                position = position + 1;
                begin = position;
                tempNode = rootNode;
            } else {
                ++position;
            }
        }

        result.append(text.substring(begin));

        return result.toString();
    }
    public static void main(String[] argv) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("好色");
        s.addWord("赌博");
        System.out.print(s.filter("你好X色**情XX。。赌博..."));
    }
}

