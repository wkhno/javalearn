[TOC]



## 面经javaSE

#### JDK

1.ConcurrentHashMap，HashMap，Hashtable的区别

分段锁，将锁的粒度变小。兼顾两者，保证线程安全和性能。

Collections.synchronizedMap()，工具类提供了同步包装器的方法，来返回具有线程安全的集合对象

比HashTable效率高的原因

把数据分段，执行分段锁（分离锁），核心把锁的范围变小，这样出现并发冲突的概率就变小
在保存的时候，计算所存储的数据是属于哪一段，只锁当前这一段。

- **分段锁（分离锁）是JDK1.8之前的一种的方案，JDK1.8之后做了优化。**

**1，数据结构**

JDK1.7采用链表的方式，而JDK1.8则采用链表+红黑树的方式

**2，发生hash碰撞之后**

JDK1.7发生碰撞之后，会采用链表的方式来解决

JDK1.8发生碰撞之后，默认采用链表，但当链表的长度超过8，且数组容量超过64时，会转换为红黑树存储

**3，保证并发安全**

JDK1.7采用分段锁的方式，而JDK1.8采用CAS和synchronized的组合模式

**4，查询复杂度**

JDK1.7采用链表的方式，时间复杂度为O(n)，而JDK1.8在采用红黑树的方式时，时间复杂度为O(log(n))

三者在开发中如何选择：

1.优先选择HashMap

如果不是多个线程访问同一资源，局部变量

2.全局变量，多个线程共享访问选择ConcurrentHashMap



## 集合面试题

#### ArrayList扩容 HashMap扩容

- 当 要 add 进第1个元素时，minCapacity为1，在Math.max()方法比较后，minCapacity 为10。
- 所以 ArrayList 每次扩容之后容量都会变为原来的 1.5 倍左右（oldCapacity为偶数就是1.5倍，否则是1.5倍左右）！ 奇偶不同，比如 ：10+10/2 = 15, 33+33/2=49。如果是奇数的话会丢掉小数.

HashMap扩容

-  扩容后大小是扩容前的2倍；
- 数据搬迁，从旧table迁到扩容后的新table。
  为避免碰撞过多，先决策是否需要对每个Entry链表结点重新hash，然后根据hash值计算得到bucket下标，然后使用头插法做结点迁移。



#### Map的遍历

1.Iterator迭代器 可用做Remove操作

```java
Iterator<Entry<String, Integer>> iterator = testMap.entrySet().iterator();
while (iterator.hasNext()) {
    Entry<String, Integer> next = iterator.next();
    System.out.println(next.getKey() + ":" + next.getValue());
}
```

2.最常见的使用方式，可同时得到key、value值

```java
for (Map.Entry<String, Integer> entry : testMap.entrySet()) {
    System.out.println(entry.getKey() + ":" + entry.getValue());
}
```

3.forEach 不推荐

4.keySet Iterator

```java
Iterator<String> keyIterator = testMap.keySet().iterator();
while (keyIterator.hasNext()) {
    String key = keyIterator.next();
    System.out.println(key + ":" + testMap.get(key));
}
```



#### Map删除元素

```java
Iterator<Entry<String, Integer>> iterator = testMap.entrySet().iterator();
while (iterator.hasNext()) {
    Entry<String, Integer> next = iterator.next();
    System.out.println(next.getKey() + ":" + next.getValue());
    if (next.getKey().equals("s2")) {
        iterator.remove();
    }
}
```

#### fail-fast 

尽早发现问题，交由上层处理。

在对Map的做迭代(Iterator)操作时，会将**`modCount`**域变量赋值给**`expectedModCount`**局部变量。在迭代过程中，用于做内容修改次数的一致性校验。若此时有其他线程或本线程的其他操作对此Map做了内容修改时，那么就会导致**modCount**和**expectedModCount**不一致，立即抛出异常**`ConcurrentModificationException`**



#### JDK8 HashMap与JDK7 区别

尾插后链表长度>=TREEIFY_THRESHOLD&&哈希表长度>=MIN_TREEIFY_CAPACITY，转为红黑树。

头插改成尾插 防止出现循环链表。

对扩容中的rehash做了优化：

// 这种规律可发现，[oldCap, (2*oldCap-1)]之间的数据，                    // 以及在此基础上加n*2*oldCap的数据，都需要做迁移，剩余的则不用迁移

JDK8 扩容

```java
步骤①：若哈希table为null，或长度为0，则做一次扩容操作；
步骤②：根据index找到目标bucket后，若当前bucket上没有结点，那么直接新增一个结点，赋值给该bucket；
步骤③：若当前bucket上有链表，且头结点就匹配，那么直接做替换即可；
步骤④：若当前bucket上的是树结构，则转为红黑树的插入操作；
步骤⑤：若步骤①、②、③、④都不成立，则对链表做遍历操作。
    a) 若链表中有结点匹配，则做value替换；
    b）若没有结点匹配，则在链表末尾追加。同时，执行以下操作：
       i) 若链表长度大于TREEIFY_THRESHOLD，则执行红黑树转换操作；
       ii) 若条件i) 不成立，则执行扩容resize()操作。
以上5步都执行完后，再看当前Map中存储的k-v对的数量是否超出了threshold，若超出，还需再次扩容。
```



红黑树的特性:
（1）每个节点或者是黑色，或者是红色。
（2）根节点是黑色。
（3）每个叶子节点（NIL）是黑色。 [注意：这里叶子节点，是指为空(NIL或NULL)的叶子节点！]
（4）如果一个节点是红色的，则它的子节点必须是黑色的。
（5）从一个节点到该节点的子孙节点的所有路径上包含相同数目的黑节点。[这里指到叶子节点的路径]

红节点之父必为黑色，因此树中任一通路都不含相邻的红节点。

**在每次插入或删除操作之后的重平衡过程中，全树拓扑结构的更新仅涉及常数个节点。尽管最坏情况下需对多达logn个节点重染色，但就分摊意义而言仅为O(1)个**

[AVL树](https://blog.csdn.net/canot/article/details/78946450)尽管可以保证最坏情况下的单次操作速度，但需在节点中嵌入平衡因子等标识;更重要的是，删除操作之后的重平衡可能需做多达logn次旋转，从而频繁地导致全树整体拓扑结构的大幅度变化。



#### HashMap与HashTable的异同

- 底层都是使用哈希表 + 链表的实现方式。

**区别**：

- 从层级结构上看，HashMap、HashTable有一个共用的**`Map`**接口。另外，HashTable还单独继承了一个抽象类**`Dictionary`**；
- HashTable诞生自JDK1.0，HashMap从JDK1.2之后才有；
- HashTable线程安全，HashMap线程不安全；
- 初始值和扩容方式不同。HashTable的初始值为11，扩容为原大小的`2*d+1`。容量大小都采用奇数且为素数，且采用取模法，这种方式散列更均匀。但有个缺点就是对素数取模的性能较低（涉及到除法运算），而HashTable的长度都是2的次幂，设计就较为巧妙，前面章节也提到过，这种方式的取模都是直接做位运算，性能较好。
- HashMap的key、value都可为null，且value可多次为null，key多次为null时会覆盖。当HashTable的key、value都不可为null，否则直接NPE(NullPointException)。



#### HashMap 不安全在哪？

- 数据覆盖问题
- 扩容时导致死循环
- 只有JDK7及以前的版本会存在死循环现象

#### CAS存在的问题及解决

CAS虽然很高效的解决原子操作，但是CAS仍然存在三大问题。ABA问题，循环时间长开销大和只能保证一个共享变量的原子操作

\1.  **ABA问题**。因为CAS需要在操作值的时候检查下值有没有发生变化，如果没有发生变化则更新，但是如果一个值原来是A，变成了B，又变成了A，那么使用CAS进行检查时会发现它的值没有发生变化，但是实际上却变化了。ABA问题的解决思路就是使用版本号。在变量前面追加上版本号，每次变量更新的时候把版本号加一，那么A－B－A 就会变成1A-2B－3A。

**从Java1**.5开始JDK的atomic包里提供了一个类AtomicStampedReference来解决ABA问题。这个类的compareAndSet方法作用是首先检查当前引用是否等于预期引用，并且当前标志是否等于预期标志，如果全部相等，则以原子方式将该引用和该标志的值设置为给定的更新值。

关于ABA问题参考文档: http://blog.hesey.net/2011/09/resolve-aba-by-atomicstampedreference.html

```java
public class StampedReferenceDemo {
    public static void main(String[] args) {
        AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100,1);
        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 的版本号为：" + stamp);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stampedReference.compareAndSet(100, 101, stampedReference.getStamp(), stampedReference.getStamp() + 1 );
            stampedReference.compareAndSet(101, 100, stampedReference.getStamp(), stampedReference.getStamp() + 1 );
        },"A").start();

        new Thread(() -> {
            int stamp = stampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + " 的版本号为：" + stamp);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = stampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(b); // false
            System.out.println(stampedReference.getReference()); // 100
            System.out.println(stampedReference.getStamp()); // 3

        },"B").start();

    }
}

```

**2. 循环时间长开销大**。自旋CAS如果长时间不成功，会给CPU带来非常大的执行开销。如果JVM能支持处理器提供的pause指令那么效率会有一定的提升，pause指令有两个作用，第一它可以延迟流水线执行指令（de-pipeline）,使CPU不会消耗过多的执行资源，延迟的时间取决于具体实现的版本，在一些处理器上延迟时间是零。第二它可以避免在退出循环的时候因内存顺序冲突（memory order violation）而引起CPU流水线被清空（CPU pipeline flush），从而提高CPU的执行效率。

 

**3. 只能保证一个共享变量的原子操作**。当对一个共享变量执行操作时，我们可以使用循环CAS的方式来保证原子操作，但是对多个共享变量操作时，循环CAS就无法保证操作的原子性，这个时候就可以用锁，或者有一个取巧的办法，就是把多个共享变量合并成一个共享变量来操作。比如有两个共享变量i＝2,j=a，合并一下ij=2a，然后用CAS来操作ij。从Java1.5开始JDK提供了**AtomicReference类来保证引用对象之间的原子性，你可以把多个变量放在一个对象里来进行CAS操作。**



通常CAS用于同步得方式是从通过Unsafe获得内存地址地址V读取atomicObject的原始值,然后和预期原值作比较计算,如果从地址V读出来的原始值和预期原始值一样,则把新值赋值到位置V上,也就是栈中的地址V对应的堆中的值就被改成新值了.

在Java中 CAS 底层使用的就是自旋锁 + UnSafe类。

#### UnSafe类

是CAS的核心类，由于Java无法直接访问底层系统，所以要通过本地的native方法进行访问，UnSafe类就相当于一个后门，基于该类可以直接操作特定内存中的数据，其内部就像C的指针一样操作内存。观察UnSafe类的源码，可以看到UnSafe类都是native方法，也就是说Unsafe类都是直接调用操作系统底层资源执行任务。



