package com.chung.baidumap;

import java.io.Serializable;

/**
 * @ClassName: LocationBean
 * @Description: ��λ��Ϣʵ����
 * @author ���
 * @date 2014��11��3�� ����2:30:03
 * 
 */
public class LocationBean implements /* Parcelable, */Cloneable, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String uid;
	public String locName;// ����
	public String province;// ʡ��
	public String city;// ����
	public String district;// ����
	public String street;// �ֵ�
	public String streetNum;// �ֵ�̖
	public Double latitude;// γ��
	public Double longitude;// ����
	public String time;
	public int locType;
	public float radius;
	// gps���е�
	public float speed;
	public int satellite;
	public float direction;
	// wifi���е�
	public String addStr;// �����ַ
	public int operationers;
	// �û���Ϣ��
	public String userId;
	public String userName;
	public String userAvator;
	// �����������ϸ����
	public String detailAddInput;

	@Override
	public Object clone() {
		LocationBean o = null;
		try {
			// Object�е�clone()ʶ�����Ҫ���Ƶ�����һ������
			o = (LocationBean) super.clone();
		} catch (CloneNotSupportedException e) {
		}
		return o;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String Street) {
		this.street = Street;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getLocType() {
		return locType;
	}

	public void setLocType(int locType) {
		this.locType = locType;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public int getSatellite() {
		return satellite;
	}

	public void setSatellite(int satellite) {
		this.satellite = satellite;
	}

	public String getAddStr() {
		return addStr;
	}

	public void setAddStr(String addStr) {
		this.addStr = addStr;
	}

	public int getOperationers() {
		return operationers;
	}

	public void setOperationers(int operationers) {
		this.operationers = operationers;
	}

	public float getDirection() {
		return direction;
	}

	public void setDirection(float direction) {
		this.direction = direction;
	}

	// @Override
	// public int describeContents() {
	// return 0;
	// }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAvator() {
		return userAvator;
	}

	public void setUserAvator(String userAvator) {
		this.userAvator = userAvator;
	}

	public String getDetailAddInput() {
		return detailAddInput;
	}

	public void setDetailAddInput(String detailAddInput) {
		this.detailAddInput = detailAddInput;
	}

	// @Override
	// public void writeToParcel(Parcel dest, int flags) {
	// dest.writeString(locName);
	// dest.writeString(province);
	// dest.writeString(city);
	// dest.writeString(district);
	// dest.writeString(street);
	// dest.writeString(addStr);
	// dest.writeDouble(latitude);
	// dest.writeDouble(longitude);
	// dest.writeString(userId);
	// dest.writeString(userName);
	// dest.writeString(userAvator);
	// dest.writeString(detailAddInput);
	// }

	// public static final Parcelable.Creator<LocationBean> CREATOR = new
	// Parcelable.Creator<LocationBean>() {
	//
	// public LocationBean createFromParcel(Parcel source) {
	// LocationBean loc = new LocationBean();
	// loc.setLocName(source.readString());
	// loc.setProvince(source.readString());
	// loc.setCity(source.readString());
	// loc.setDistrict(source.readString());
	// loc.setStreet(source.readString());
	// loc.setAddStr(source.readString());
	// loc.setLatitude(source.readDouble());
	// loc.setLongitude(source.readDouble());
	// loc.setUserId(source.readString());
	// loc.setUserName(source.readString());
	// loc.setUserAvator(source.readString());
	// loc.setDetailAddInput(source.readString());
	// return loc;
	// }
	//
	// public LocationBean[] newArray(int size) {
	// return new LocationBean[size];
	// }
	// };

}

