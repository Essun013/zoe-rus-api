package com.zoe.rus.uc.user;import com.zoe.commons.ctrl.http.upload.UploadListener;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;/** * @author lpw */@Controller(UserModel.NAME + ".upload-listener.portrait")public class PortraitUploadListenerImpl implements UploadListener {    @Autowired    protected UserService userService;    @Override    public String getKey() {        return "uc.user.portrait";    }    @Override    public boolean isUploadEnable(String contentType, String name) {        return contentType.startsWith("/image/");    }    @Override    public String upload(String name, String size, String uri) {        userService.portrait(uri);        return uri;    }    @Override    public int[] getImageSize() {        return new int[]{64, 64};    }}