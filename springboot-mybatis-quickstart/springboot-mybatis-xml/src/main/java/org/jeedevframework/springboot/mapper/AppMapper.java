package org.jeedevframework.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.jeedevframework.springboot.entity.App;

@Mapper
public interface AppMapper {

	public List<App> findAll();

	public App findOne(int id);

	public void addOne(App app);

	public void updateOne(App app);

	public void deleteOne(int id);
}
