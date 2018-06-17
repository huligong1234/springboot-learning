package org.jeedevframework.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeedevframework.springboot.entity.App;

@Mapper
public interface AppMapper {

	@Results({ 
		@Result(column = "gmt_create", property = "gmtCreate"),
		@Result(column = "gmt_modified", property = "gmtModified"),
		@Result(column = "is_deleted", property = "isDeleted"), 
		@Result(column = "re_order", property = "reOrder"),
		@Result(column = "app_code", property = "appCode"), 
		@Result(column = "app_name", property = "appName") 
		})
	@Select("SELECT * FROM app")
	public List<App> findAll();

	@Results({ 
		@Result(column = "gmt_create", property = "gmtCreate"),
		@Result(column = "gmt_modified", property = "gmtModified"),
		@Result(column = "is_deleted", property = "isDeleted"), 
		@Result(column = "re_order", property = "reOrder"),
		@Result(column = "app_code", property = "appCode"), 
		@Result(column = "app_name", property = "appName") 
		})
	@Select("SELECT * FROM app WHERE id=#{id}")
	public App findOne(int id);

	@Insert("INSERT INTO app(gmt_create,app_code,app_name) VALUES (NOW(),#{appCode},#{appName})")
	public void addOne(App app);

	@Results({
        @Result(column = "app_name",property = "appName")
	})
	@Update("UPDATE app SET app_name=#{appName},gmt_modified=NOW() WHERE id=#{id}")
	public void updateOne(App app);

	@Delete("DELETE FROM app WHERE id=#{id}")
	public void deleteOne(int id);
}
