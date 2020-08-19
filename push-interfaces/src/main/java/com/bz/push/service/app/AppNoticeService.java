package com.bz.push.service.app;

import java.util.Map;

import com.bz.push.model.app.AppNotice;

public interface AppNoticeService {
    Map<String,Object> add(AppNotice appNotice);
}
