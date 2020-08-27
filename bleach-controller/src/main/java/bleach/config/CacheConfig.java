package drr.config;

import java.io.Serializable;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import drr.constant.cache.DRRRedisCacheConst;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport{

	@Autowired
	private RedisConnectionFactory factory;

	@Bean(name = "redisTemplate")
	public RedisTemplate<Serializable, Serializable> redisTemplate() {
		RedisTemplate<Serializable, Serializable> template = new RedisTemplate<>();
		// string结构序列化
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		template.setConnectionFactory(factory);
		// 暂不支持事务
		template.setEnableTransactionSupport(false);

		return template;
	}

	@Bean(name="redisCacheManager")
	public CacheManager cacheManager() {
		//用户信息缓存配置
		RedisCacheConfiguration userCacheRedisConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMinutes(TIME_OUT)).prefixKeysWith(DRRRedisCacheConst.KEY_PREFIX_DRR+":USER:")
				.disableCachingNullValues()
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair
						.fromSerializer((new GenericJackson2JsonRedisSerializer())));

		return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
				.withCacheConfiguration("userCache", userCacheRedisConfig).disableCreateOnMissingCache().build();
	}

	// TODO
	private final static long TIME_OUT = 30L;
}
