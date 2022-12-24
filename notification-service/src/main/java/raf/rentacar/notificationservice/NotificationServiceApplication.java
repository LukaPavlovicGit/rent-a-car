package raf.rentacar.notificationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

//	@Component
//	public class ClearCacheTask {
//		@Autowired
//		private CacheManager cacheManager;
//
//		@Scheduled(fixedRateString = "${clear.all.cache.fixed.rate}", initialDelayString = "${clear.all.cache.init.delay}") // reset cache every hr, with delay of 1hr after app start
//		public void reportCurrentTime() {
//			cacheManager.getCacheNames().parallelStream().forEach(name -> cacheManager.getCache(name).clear());
//		}
//	}

}
