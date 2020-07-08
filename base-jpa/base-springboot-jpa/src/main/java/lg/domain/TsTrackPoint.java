package lg.domain;

import lombok.Data;

@Data
public class TsTrackPoint {
	/// 唯一标识态势点
	public String id;

	/// 来报单位
	public String lbdw;

	/// 目标名称
	public String mbmc;

	/// 目标编号
	public String mbbh;

	/// 目标数量
	public Long mbsl;

	/// 经度
	public Double lon;

	/// 纬度
	public Double lat;

	/// 高深度
	public Double alt;

	/// 航向以正北方为0度，按顺时针方向计数0o ~360o。默认值为-100000，表示没有说明。
	public Long hx;

	/// 航速默认值为-100000，表示没有说明。
	public Long hs;

	/// 位置时间(DateTime)
	public String wzsj;

	/// 上报时间(DateTime)
	public String sbsj;

	/// 目标类别
	public String mblb;

	/// 目标性质
	public String mbxz;

	/// 目标种类
	public String mbzl;

	/// 装备型号
	public String zbxh;

	/// 国家地区
	public String gjdq;

	/// 呼号
	public String hh;

	/// 批号
	public String ph;

	/// 机（舷）号
	public String jxh;

	/// 目标平面位置的误差半径。默认值为-100000，表示没有说明。
	public String dwwc;

	/// 目标高度的误差。默认值为-100000，表示没有说明。
	public String gcwc;

	/// 目标状态
	public String mbzt;

	/// 重要程度
	public String zycd;

	/// 可信度
	public String kxd;

	/// 敌我属性
	public String dwsx;

	/// 军种
	public String jz;

	/// 隶属单位
	public String lsdw;

	/// 威胁半径
	public Long wxbj;

	/// 任务
	public String rw;

	/// 手段简称
	public String sdjc;

	/// 原始数据ID
	public String ysid;

	/// 接收时间
	public String jssj;

	/// 系统目标编号
	public String xtmbbh;

	/// 原始批号
	public String ysph;

	/// 备注
	public String bz;

	/// 处理流程
	public String cllc;

	/// 置信度
	public Double zxd;

	/// 融合方式
	public String rhfs;

	/// 来源标识
	public String lybs;

	/// 疑似目标
	public String ysmb;

	/// 异常标记
	public String ycbj;

	/// 扩展字段
	public String kzzd;

	/// 历史数据
	public String lssj;

	/// 操作指令
	public String czzl;

	/// 数据种类(卫星数据、实时数据、专用数据)
	public String sjzl;

	/// 态势种类(值班态势)
	public String tszl;
	
	/// 拼音字段(全) 小写
    public String pinyinAll;
		
    /// 拼音字段(首字母) 小写
    public String pinyinFirst;
}
