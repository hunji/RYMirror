package com.hunji.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 项目的一些个性化配置
 *
 * @author hunji
 * @version 1.0
 * @date 2023/7/28 15:39
 */
@Component
@ConfigurationProperties(prefix = "ruoyi")
@Data
public class RuoYiConfig {
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 实例演示开关 */
    private boolean demoEnabled;

    /** 上传路径 */
    private static String profile;

    /** 获取地址开关 */
    private static boolean addressEnabled;

    /** 验证码类型 */
    private static String captchaType;
    public boolean isDemoEnabled()
    {
        return demoEnabled;
    }
    public static boolean isAddressEnabled()
    {
        return addressEnabled;
    }
    public static String getProfile()
    {
        return profile;
    }
    public static String getCaptchaType() {
        return captchaType;
    }
    /**
     * 获取导入上传路径
     */
    public static String getImportPath()
    {
        return getProfile() + "/import";
    }

    /**
     * 获取头像上传路径
     */
    public static String getAvatarPath()
    {
        return getProfile() + "/avatar";
    }

    /**
     * 获取下载路径
     */
    public static String getDownloadPath()
    {
        return getProfile() + "/download/";
    }

    /**
     * 获取上传路径
     */
    public static String getUploadPath()
    {
        return getProfile() + "/upload";
    }
}
