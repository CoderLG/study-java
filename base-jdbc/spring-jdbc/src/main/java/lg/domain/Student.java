package lg.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import java.util.Date;


//@JsonPropertyOrder(value = {"age","studentName"})  改变顺序
@Data
public class Student {

	//@JsonIgnore     //序列化和反序列化都不要   字段为数据库字段，前端给你传的字段 转换为数据库字段
	private int id;//学生学号

	@JsonProperty("name")		//别名
	private String studentName;//学生姓名

	private int age;//年龄

	@JsonIgnore
	@JsonInclude(JsonInclude.Include.NON_NULL)				//null 则忽略
	//@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")  日期格式化
	private Date createTime;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Teacher teacher;

}
