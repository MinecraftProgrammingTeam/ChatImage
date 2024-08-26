package top.xzynb.chatimage.utils;

/*
解析像这样的CQCode：
```
[CQ:type,arg1=xxx,arg2=123,arg3=sss]
```
 */

import top.xzynb.chatimage.exceptions.NotCQCodeException;

import java.util.HashMap;

public class CQCode {
    private final String type;
    private final HashMap<String,String> argMap;
    private final String cqCode;

    public CQCode(String cqCode) {
        this.argMap = new HashMap<>();
        this.type = cqCode;
        this.cqCode = cqCode;
        this.parse();
    }

    public String getType() {
        return type;
    }

    public String getArg(String key) {
        return argMap.get(key);
    }

    public String getCqCode() {
        return cqCode;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[CQ:");
        sb.append(type);
        for (String key : argMap.keySet()) {
            sb.append(",").append(key).append("=").append(argMap.get(key));
        }
        sb.append("]");
        return sb.toString();
    }

    public void parse() {
        if (!cqCode.startsWith("[CQ:") || !cqCode.endsWith("]")) {
            throw new NotCQCodeException("Not a CQCode");
        }
        String[] split = cqCode.substring(4, cqCode.length() - 1).split(",");
        for (String s : split) {
            String[] kv = s.split("=");
            argMap.put(kv[0], kv[1]);
        }
    }
}
