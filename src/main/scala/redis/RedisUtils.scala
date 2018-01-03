package redis

import java.util

import org.apache.commons.lang3.StringUtils
import redis.clients.jedis.{Jedis, JedisPool, JedisPoolConfig}

/**
  * Created by xiong on 2016/12/6.
  */

object RedisUtils {

  private var jedisPool: JedisPool = _

  def redisPool(ip: String, port: Int, pass: String): JedisPool = {
    if (!StringUtils.isBlank(pass)) jedisPool = new JedisPool(getStaticConfig, ip, port, 10000, pass)
    else jedisPool = new JedisPool(getStaticConfig, ip, port, 10000)
    jedisPool
  }

  def getStaticConfig: JedisPoolConfig = {
    val config: JedisPoolConfig = new JedisPoolConfig
    config.setMaxTotal(10) // 设置最大连接数
    config.setMaxIdle(5) // 设置最大空闲数
    config.setMaxWaitMillis(3000) // 设置超时时间
    config.setTestOnBorrow(false)
    config
  }


}
