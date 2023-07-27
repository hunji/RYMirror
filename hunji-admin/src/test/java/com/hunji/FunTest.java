package com.hunji;

import org.junit.jupiter.api.Test;

/**
 * TODO
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/10 10:35
 */
public class FunTest {
    @Test
    public void test1(){
        int hashCode = 0;
        for (int i = 0; i < 16; i++) {
            hashCode = i * 0x61c88647 + 0x61c88647;
            int idx = hashCode & 15;
            System.out.println("斐波那契散列：" + idx + " 普通散列：" + (String.valueOf(i).hashCode() & 15));
        }
    }
}
