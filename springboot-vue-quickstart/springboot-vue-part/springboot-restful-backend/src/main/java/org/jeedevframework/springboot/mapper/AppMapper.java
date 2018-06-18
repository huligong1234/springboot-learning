package org.jeedevframework.springboot.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.jeedevframework.springboot.entity.App;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

@Mapper
public interface AppMapper extends BaseMapper<App> { 

	
	/**
     * <p>
     * 查询 : 根据isDeleted状态查询列表，分页显示
     * </p>
     *
     * @param page
     *            翻页对象，可以作为 xml 参数直接使用，传递参数 Page 即自动分页
     * @param isDeleted
     *            是否逻辑删除
     * @return
     */
    List<App> findAppList(Pagination page, Integer isDeleted);
}
