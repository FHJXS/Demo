package com.news.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.news.dao.BaseDao;
import com.news.service.BaseService;

public class BaseServiceImpl<T> extends ServiceImpl<BaseDao<T>, T> implements BaseService<T> {

}
