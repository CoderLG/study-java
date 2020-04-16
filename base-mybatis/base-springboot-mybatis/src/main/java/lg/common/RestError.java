package lg.common;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
public enum RestError {
	BASE_ERROR("00101","系统运行时异常!"),
	OTHER_ERROR("00102","未知异常!"),
	IO_ERROR("00120","文件IO异常!"),
	CLIENT_TOKEN_ERROR("00403", "客户端token异常!"),
	USER_TOKEN_ERROR("00401", "用户token异常!"),
	DATABASE_ERROR("00157","数据库异常!"),

	BASEIMAGE_QUERY_DADABASE_ERROR("00157","基础底图查询,数据库异常!"),
	BASEIMAGE_SAVE_DADABASE_ERROR("00257","基础底图保存,数据库异常!"),
	BASEIMAGE_DELETE_DADABASE_ERROR("00357","基础底图删除,数据库异常!"),
	BASEIMAGE_LAYER_QUERY_DADABASE_ERROR("01057","基础底图关联的图层查询,数据库异常!"),
	BASEIMAGE_LAYER_SAVE_DADABASE_ERROR("01157","基础底图关联的图层保存,数据库异常!"),
	BASEIMAGE_LAYER_DELETE_DADABASE_ERROR("01157","基础底图关联的图层删除,数据库异常!");
	private String code;
	private String message;
	private String reason;
	private RestError(String code, String message) {
		this.code = String.format("100%s",code) ;
		this.message = message;
	}
	public RestError setReason(String reason){
		this.reason = reason;
		return this;
	}


	@Override
	public String toString() {
		JSONObject object = new JSONObject();
		object.put("code",code);
		object.put("message",message);
		object.put("reason",reason);
		return object.toString();
	}
}
