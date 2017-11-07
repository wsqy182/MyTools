package com.xq.tool.mvc.service;

import com.xq.tool.core.utils.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class BaseService {
  @Autowired
  BaseDao baseDao;
}
