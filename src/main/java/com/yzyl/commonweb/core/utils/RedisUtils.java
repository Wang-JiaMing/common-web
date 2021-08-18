package com.yzyl.commonweb.core.utils;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @projectName:common-web
 * @packageName:com.wonders.commonweb.core.utils
 * @authorName:wangjiaming
 * @createDate:2021/6/9
 * @editor:IntelliJ IDEA
 * @other:redis高速缓存集成工具类
 **/
public class RedisUtils {

    /*
     *  是否为集群
     */
    public static boolean isCluster = false;
    /*
     * server信息
     */
    public static String serverInfo = null;
    /*
     *  在应用初始化的时候生成连接池
     */
    private static Pool<Jedis> pool = null;
    /*
     * server信息List
     */
    private static List<String> serverList = new ArrayList<String>();


    static {
        try {
            Properties prop = new Properties();
            InputStream in = RedisUtils.class.getClassLoader().getResourceAsStream("application.properties");
            prop.load(in);
            in.close();

            String mode = "cluster";
            if ("cluster".equals(mode)) {
                //集群模式
                isCluster = true;
            } else {
                //单服务器模式
                isCluster = false;
            }
            String server = prop.getProperty("REDIS_SERVER_URL");
            System.out.println("mode:" + mode + ".servers" + server);
            serverInfo = server;
            String[] servers = server.split(",");
            for (String str : servers) {
                serverList.add(str.trim());
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static Pool<Jedis> getPool() {

        if (null == pool) {
            if (isCluster) {
                Set<String> sentinels = new HashSet<String>(16);
                if (serverList.size() > 0) {
                    for (String server : serverList) {
                        sentinels.add(server);
                    }
                }

                GenericObjectPoolConfig config = new GenericObjectPoolConfig();

                //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
                config.setBlockWhenExhausted(true);

                //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
                config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

                //是否启用pool的jmx管理功能, 默认true
                config.setJmxEnabled(true);

                //是否启用后进先出, 默认true
                config.setLifo(true);

                //最大连接数, 默认8个
                config.setMaxTotal(1500);

                //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
                config.setMaxWaitMillis(-1);

                //最大空闲连接数, 默认8个
                config.setMaxIdle(60);

                //最小空闲连接数, 默认0
                config.setMinIdle(0);

                //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
                config.setMinEvictableIdleTimeMillis(1800000);

                //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
                config.setNumTestsPerEvictionRun(3);

                //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
                config.setSoftMinEvictableIdleTimeMillis(1800000);

                //在获取连接的时候检查有效性, 默认false
                config.setTestOnBorrow(false);

                //在空闲时检查有效性, 默认false
                config.setTestWhileIdle(false);

                //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
                config.setTimeBetweenEvictionRunsMillis(-1);

                pool = new JedisSentinelPool("mymaster", sentinels, config, 100000);
            } else {
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxIdle(10);
                config.setMaxTotal(30);
                config.setMaxWaitMillis(3 * 1000);

                String server = serverList.get(0);

                String[] temp = server.split(":");
                int port = Integer.parseInt(temp[1]);

                pool = new JedisPool(config, temp[0], port);
            }

        }

        return pool;

    }

    /**
     * @throws Exception
     * @名称 get
     * @描述 获取字符型key的value数据
     * @参数 @param key
     * @参数 @return
     * @返回值 String
     * @作者
     * @时间 2015年7月11日 下tokenToType午3:58:32
     */
    public static String get(String key) {

        String value = null;
        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
//			throw e;
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return value;
    }

    /**
     * @名称 get
     * @描述 获取字符型key的value数据
     * @参数 @param key
     * @参数 @return
     * @返回值 byte[]
     * @作者
     * @时间 2015年7月11日 下午4:00:41
     */
    public static byte[] get(byte[] key) {

        byte[] value = null;
        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return value;
    }

    /**
     * @名称 set
     * @描述 设置字符型key和value
     * @参数 @param key
     * @参数 @param value
     * @返回值 void
     * @作者
     * @时间 2015年7月11日 下午4:01:01
     */
    public static void set(String key, String value) {
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void set(String key, String value, int time) {
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key, value);
            jedis.expire(key, time);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @名称 set
     * @描述 设置字符型key和value
     * @参数 @param key
     * @参数 @param value
     * @返回值 void
     * @作者
     * @时间 2015年7月11日 下午4:01:43
     */
    public static void set(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @名称 set
     * @描述 设置字符型key和value 及过期时间
     * @参数 @param key
     * @参数 @param value
     * @参数 @param time
     * @返回值 void
     * @作者
     * @时间 2015年7月11日 下午4:02:22
     */
    public static void set(byte[] key, byte[] value, int time) {
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.set(key, value);
            jedis.expire(key, time);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @名称 hset
     * @描述 保存散列key的 field和 value
     * @参数 @param key
     * @参数 @param field
     * @参数 @param value
     * @返回值 void
     * @作者
     * @时间 2015年7月11日 下午4:02:51
     */
    public static void hset(byte[] key, byte[] field, byte[] value) {

        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @名称 hset
     * @描述 保存散列key的 field和 value
     * @参数 @param key
     * @参数 @param field
     * @参数 @param value
     * @返回值 void
     * @作者
     * @时间 2015年7月11日 下午4:03:57
     */
    public static void hset(String key, String field, String value) {

        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.hset(key, field, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @名称 mhset
     * @描述 散列对象设置
     * @参数 @param key
     * @参数 @param field
     * @参数 @param value
     * @返回值 void
     * @作者
     * @时间 2015年7月11日 上午9:54:10
     */
    public static void mhset(String key, Map<String, String> hash) {

        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.hmset(key, hash);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @名称 llen
     * @描述 查询list长度
     * @参数 @param key
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2015年7月11日 下午4:04:14
     */
    public static long llen(String key) {

        Jedis jedis = null;
        long length = 0;

        try {
            pool = getPool();
            jedis = pool.getResource();
            length = jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return length;
    }

    /**
     * @名称 llen
     * @描述 查询list长度
     * @参数 @param key
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2015年7月11日 下午4:51:57
     */
    public static long llen(byte[] key) {

        long len = 0;
        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.llen(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return len;
    }

    /**
     * @名称 hget
     * @描述 查询散列key中field的value
     * @参数 @param key
     * @参数 @param field
     * @参数 @return
     * @返回值 String
     * @作者
     * @时间 2015年7月11日 下午4:04:47
     */
    public static String hget(String key, String field) {

        String value = null;
        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return value;
    }

    /**
     * @名称 hdel
     * @描述 散列删除
     * @参数 @param key
     * @参数 @param field
     * @参数 @return
     * @返回值 String
     * @作者
     * @时间 2015年11月10日 下午6:24:51
     */
    public static Long hdel(String key, String field) {

        long value = 0;
        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.hdel(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return value;
    }

    /**
     * @名称 hgetAll
     * @描述 查询散列key 所有键值对
     * @参数 @param key
     * @参数 @return
     * @返回值 Map<String, String>
     * @作者
     * @时间 2015年7月12日 上午11:44:01
     */
    public static Map<String, String> hgetAll(String key) {

        Map<String, String> value = null;
        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return value;
    }

    /**
     * 获取数据
     *
     * @param key
     * @return
     */
    public static byte[] hget(byte[] key, byte[] field) {

        byte[] value = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            value = jedis.hget(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return value;
    }

    public static void hdel(byte[] key, byte[] field) {

        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.hdel(key, field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void lpush(byte[] key, byte[] value) {

        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.lpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void lpush(String key, String value) {

        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.lpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void rpush(byte[] key, byte[] value) {

        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.rpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void rpush(String key, String value) {

        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.rpush(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static String rpoplpush(String key, String destination) {

        Jedis jedis = null;
        String outValue = "";
        try {
            pool = getPool();
            jedis = pool.getResource();
            outValue = jedis.rpoplpush(key, destination);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return outValue;
    }

    public static void rpoplpush(byte[] key, byte[] destination) {

        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.rpoplpush(key, destination);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static List<byte[]> lpopList(byte[] key) {

        List<byte[]> list = null;
        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            list = jedis.lrange(key, 0, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return list;
    }

    public static byte[] rpop(byte[] key) {

        byte[] bytes = null;
        Jedis jedis = null;

        try {
            pool = getPool();
            jedis = pool.getResource();
            bytes = jedis.rpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return bytes;
    }

    public static String rpop(String key) {

        String result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.rpop(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    public static List<String> brpop(String key) {

        List<String> result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.brpop(0, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    public static void hmset(Object key, Map<String, String> hash) {

        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.hmset(key.toString(), hash);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static void hmset(Object key, Map<String, String> hash, int time) {

        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.hmset(key.toString(), hash);
            jedis.expire(key.toString(), time);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public static List<String> hmget(Object key, String... fields) {

        List<String> result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.hmget(key.toString(), fields);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    public static Set<String> hkeys(String key) {

        Set<String> result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.hkeys(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 keys
     * @描述 查询匹配pattern的key 集合
     * @参数 @param key
     * @参数 @return
     * @返回值 Set
     * @作者
     * @时间 2015年7月11日 下午3:19:48
     */
    public static Set<String> keys(String pattern) {

        Set<String> result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    public static List<byte[]> lrange(byte[] key, int from, int to) {

        List<byte[]> result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.lrange(key, from, to);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    public static List<String> lrange(String key, int from, int to) {

        List<String> result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.lrange(key, from, to);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    public static Map<byte[], byte[]> hgetAll(byte[] key) {

        Map<byte[], byte[]> result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.hgetAll(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 del
     * @描述 删除key
     * @参数 @param key
     * @返回值 void
     * @作者
     * @时间 2015年7月11日 下午4:55:25
     */
    public static void del(byte[] key) {

        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @名称 del
     * @描述 删除key
     * @参数 @param key
     * @返回值 void
     * @作者
     * @时间 2015年7月11日 下午4:55:07
     */
    public static void del(String key) {

        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @名称 flushAll
     * @描述 清空所有数据
     * @参数
     * @返回值 void
     * @作者
     * @时间 2015年7月11日 下午4:54:47
     */
    public static void flushAll() {

        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            jedis.flushAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    /**
     * @名称 ttl
     * @描述 返回剩余时间
     * @参数 @param key
     * @参数 @return
     * -2 键不存在。
     * -1 键存在，但没有设置过期时间或者生存时间。
     * >= 0 键的剩余生存时间。
     * @返回值 long
     * @作者
     * @时间 2015年7月7日 下午5:40:04
     */
    public static long ttl(String key) {

        long time = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            time = jedis.ttl(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return time;
    }

    /**
     * @名称 expire
     * @描述 设置键的生存时间
     * @参数 @param key
     * @参数 @param seconds 时间单位 秒
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2015年7月7日 下午5:36:54
     */
    public static long expire(String key, int seconds) {

        long len = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            len = jedis.expire(key, seconds);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return len;
    }

    /**
     * @名称 setnx
     * @描述 设置不存在的key
     * @参数 @param key
     * @参数 @param value
     * @参数 @return 1:设置成功。0：设置失败。
     * @返回值 long
     * @作者
     * @时间 2015年7月8日 下午3:29:01
     */
    public static long setnx(String key, String value) {

        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.setnx(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 sismember
     * @描述 set集合操作：检查给定的元素是否存在于集合
     * @参数 @param setkey
     * @参数 @param key
     * @参数 @return true 存在， false 不存在
     * @返回值 boolean
     * @作者
     * @时间 2015年7月11日 下午4:49:54
     */
    public static boolean sismember(String setkey, String key) {

        boolean result = false;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.sismember(setkey, key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 sadd
     * @描述 set集合操作：集合中添加一个元素
     * @参数 @param setkey
     * @参数 @param memeber
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2015年7月11日 下午5:12:40
     */
    public static long sadd(String setkey, String memeber) {

        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.sadd(setkey, memeber);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 srem
     * @描述 set集合操作：集合中删除一个元素
     * @参数 @param setkey
     * @参数 @param memeber
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2015年7月11日 下午5:30:30
     */
    public static long srem(String setkey, String memeber) {

        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.srem(setkey, memeber);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 smembers
     * @描述 set集合操作：获取集合
     * @参数 @param setKey
     * @参数 @return
     * @返回值 Set<String>
     * @作者
     * @时间 2016年1月19日 下午2:04:24
     */
    public static Set<String> smembers(String setKey) {
        Set<String> result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.smembers(setKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 info
     * @描述 查看服务器当前的状态信息和统计信息
     * @参数 @return
     * @返回值 String
     * @作者
     * @时间 2015年8月3日 下午2:33:55
     */
    public static String info() {

        String result = "";
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.info();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 zadd
     * @描述
     * @参数 @return
     * @返回值 Integer reply, specifically:
     * 1 if the new element was added
     * 0 if the element was already a member of the sorted set and the score was updated
     * @作者
     * @时间 2015年8月12日 上午9:40:32
     */
    public static long zadd(String key, double score, String element) {

        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.zadd(key, score, element);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 zrem
     * @描述 TODO(根据element删除zset)
     * @参数 @param key
     * @参数 @param element
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2015-8-27 上午10:48:52
     */
    public static long zrem(String key, String element) {

        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.zrem(key, element);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 zadd
     * @描述 TODO(批量添加 zset)
     * @参数 @param key
     * @参数 @param scoreMembers
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2015-8-27 上午10:55:45
     */
    public static long zadd(String key, Map<String, Double> scoreMembers) {

        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.zadd(key, scoreMembers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 zrem
     * @描述 TODO(批量删除ZSET list elements)
     * @参数 @param key
     * @参数 @param elements
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2015-8-27 上午10:54:57
     */
    public static long zrem(String key, List<String> elements) {
        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            for (String element : elements) {
                result = jedis.zrem(key, element);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @throws Exception
     * @名称 incr
     * @描述 针对数字值递增一
     * @参数 @param key
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2015年8月14日 下午4:46:45
     */
//	public static long incr(String key) throws Exception {
    public static long incr(String key) {

        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.incr(key);
        } catch (Exception e) {
            e.printStackTrace();
//			throw e;
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @名称 decr
     * @描述 针对数字值递减一
     * @参数 @param key
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2016年3月3日 下午3:49:47
     */
    public static long decr(String key) {
        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.decr(key);
        } catch (Exception e) {
            e.printStackTrace();
//			throw e;
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return result;
    }

    /**
     * @Description: 根据参数 count 的值，移除列表中与参数 value 相等的元素。根据参数 count 的值，移除列表中与参数 value 相等的元素。
     * @param: count 的值可以是以下几种：
     * count > 0 : 从表头开始向表尾搜索，移除与 value 相等的元素，数量为 count 。
     * count < 0 : 从表尾开始向表头搜索，移除与 value 相等的元素，数量为 count 的绝对值。
     * count = 0 : 移除表中所有与 value 相等的值。
     * @return:
     * @auther:
     * @date: 2018-12-3 18:58
     */
    public static long lrem(String key, long count, String value) {
        Jedis jedis = null;
        long lrem = 0;
        try {
            pool = getPool();
            jedis = pool.getResource();
            lrem = jedis.lrem(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
//			throw e;
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }

        return lrem;
    }

    /**
     * @名称 zremByScore
     * @描述 根据分值删除element zremrangebyscore(key, min, max) ：删除名称为key的zset中score >= min且score <= max的所有元素
     * @参数 @param key
     * @参数 @param score
     * @参数 @return
     * @返回值 long
     * @作者
     * @时间 2018-9-13 下午4:16:10
     */
    public static long zremByScore(String key, String score) {
        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.zremrangeByScore(key, score, score);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @名称 exists
     * @描述 是否存在键值
     * @参数 @param key
     * @参数 @return
     * @返回值 boolean
     * @作者
     * @时间 2018-9-13 下午4:16:10
     */
    public static boolean exists(String key) {
        boolean result = false;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @名称 zrange
     * @描述 获取zset key中start到end的元素
     * @参数 @param key
     * @参数 @param start
     * @参数 @param end
     * @参数 @return
     * @返回值 Set<String>
     * @作者
     * @时间 2018-9-13 下午4:16:10
     */
    public static Set<String> zrange(String key, long start, long end) {
        Set<String> result = null;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            result = jedis.zrange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    /**
     * @名称 zcount
     * @描述 zset大小
     * @参数 @param key
     * @参数 @return
     * @返回值 boolean
     * @作者
     * @时间 2018-9-13 下午4:16:10
     */
    public static long zcount(String key) {
        long result = 0;
        Jedis jedis = null;
        try {
            pool = getPool();
            jedis = pool.getResource();
            //zcount(final String key, final String min, final String max) 无限
            result = jedis.zcount(key, "-inf", "+inf");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //返还到连接池
            if (jedis != null) {
                jedis.close();
            }
        }
        return result;
    }

    public static void main(String[] args) {
            RedisUtils.set("test","test",3000);
    }
}
