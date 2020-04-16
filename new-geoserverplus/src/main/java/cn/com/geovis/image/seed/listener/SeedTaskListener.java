package cn.com.geovis.image.seed.listener;

import org.geotools.util.logging.Logging;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class SeedTaskListener implements ISeedTaskListener {

	private AtomicLong finishedTileNum = new AtomicLong(0);
	private AtomicInteger finishedTaskNum = new AtomicInteger(0);
	private final int tasknums;
	boolean flag = true;
	private final String imageUpdateStatusUrl;
	private final RestTemplate restTemplate;
	
	private static final Logger logger = Logging.getLogger(SeedTaskListener.class);
	
	public SeedTaskListener(int tasknums,String imageServiceUrl) {
		this.tasknums = tasknums;
		imageUpdateStatusUrl = imageServiceUrl+"/image/api/v1/seed/status/update";
		restTemplate = new RestTemplate();
		
	}
	
	@Override
	public void taskStart(String layerName) {
		//
	}

	@Override
	public void taskSuccess(String layerName, long tilesTotal, long tilesDone) {
		finishedTileNum.addAndGet(tilesDone);
		if(finishedTaskNum.addAndGet(1) >= tasknums) {
			if(flag)
				taskFinished(layerName);
			else
				taskUnFinished(layerName);
		}
	}

	@Override
	public void taskBeStop(String layerName, long tilesTotal, long tilesDone) {
		flag = false;
		taskSuccess(layerName, tilesTotal, tilesDone);
	}
	
	
	private void taskUnFinished(String layerName) {
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();
		bodyMap.add("layername",layerName);
		bodyMap.add("status", "STOP");
		
		String result = restTemplate.postForObject(imageUpdateStatusUrl, bodyMap, String.class);
		String msg = "图层"+layerName+",切片未能完成,更新成功，反馈结果为"+result;
		logger.info(msg);
	}
	
	private void taskFinished(String layerName) {
		
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();
		bodyMap.add("layername",layerName);
		bodyMap.add("status", "SUCCESS");
		
		String result = restTemplate.postForObject(imageUpdateStatusUrl, bodyMap, String.class);
		String msg = "图层"+layerName+",切片完成,更新成功，反馈结果为"+result;
		logger.info(msg);
	}

}
