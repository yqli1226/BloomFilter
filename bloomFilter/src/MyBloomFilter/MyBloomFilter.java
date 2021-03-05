package MyBloomFilter;

import java.util.BitSet;
import java.util.SimpleTimeZone;

/**
 * @program: bloomFilter
 * @description:
 * @author: Mr.Li
 * @create: 2021-03-05 17:16
 **/
public class MyBloomFilter {
    //位数组的大小
    private static final int DEFAULT_SIZE = 2 << 24;
    //通过这个数组创建6个不同的哈希函数
    private static final int[] SEEDS = new int[]{3, 13, 46, 71, 91, 134};
    //位数组
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    //存放包含hash函数的类的数组
    private SimpleHash[] func = new SimpleHash[SEEDS.length];

    //初始化多个包含hash函数的类的数组，每一类中的hash函数都不一样
    public MyBloomFilter() {
        for (int i = 0; i < SEEDS.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, SEEDS[i]);
        }
    }

    //添加元素到位数组
    public void add(Object value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }

    //判断存在
    public boolean contains(Object value) {
        boolean ret = true;
        for (SimpleHash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    //静态内部类实现hash操作！
    public static class SimpleHash {
        private int cap;
        private int seed;

        public SimpleHash(int cat, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        //计算hash
        public int hash(Object value) {
            int h;
            return (value == null) ? 0 : Math.abs(seed * (cap - 1) & ((h = value.hashCode()) ^ (h >>> 16)));
        }
    }

    public static void main(String[] args) {
        String value1 = "https://www.iduoduo.work";
        String value2 = "https://www.baidu.com";
        MyBloomFilter filter = new MyBloomFilter();
        System.out.println(filter.contains(value1));
        System.out.println(filter.contains(value2));
        filter.add(value1);
        filter.add(value2);
        System.out.println(filter.contains(value1));
        System.out.println(filter.contains(value2));
    }
}

