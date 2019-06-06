package umeng;

public interface VersionParser {
    /**
     * 将服务端返回的版本数据解析为版本对象
     *
     * @param response 服务端返回的数据
     */
    Version onParse(String response);
}