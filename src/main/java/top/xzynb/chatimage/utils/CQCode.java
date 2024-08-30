package top.xzynb.chatimage.utils;

/*
解析像这样的CQCode：
```
[CQ:type,arg1=xxx,arg2=123,arg3=sss]
```
注意CQCode前后可能有普通的字符，像下面这样
```
hi, this is me[CQ:face,id=151]!
```
注意一个句子中可能包含**多个CQCode**，像下面这样：
```
hi, [CQ:image,url=https://imageure.com/1.png] how do you think of this [CQ:face,id=135]
```
你需要提取出**所有的**CQCode，并支持arg的增删改查操作
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CQCode {
    private String type;
    private HashMap<String, String> args;
    private final String originCqCode;
    private final int startIndex;
    private final int endIndex;

    public CQCode(String type, HashMap<String, String> args, String originCqCode, Integer startIndex, Integer endIndex) {
        this.type = type;
        this.args = args;
        this.originCqCode = originCqCode;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getArgs() {
        return args;
    }

    public String getArg(String key) {
        return args.get(key);
    }

    public void setArgs(HashMap<String, String> args) {
        this.args = args;
    }

    public void setArg(String key, String value) {
        args.put(key, value);
    }

    public String getOriginCqCode(){
        return originCqCode;
    }

    public int getStartIndex(){
        return startIndex;
    }

    public int getEndIndex(){
        return endIndex;
    }

    public static List<CQCode> parseCQCodes(String message) {
        List<CQCode> cqCodes = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[CQ:(.*?)]");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            Integer startIndex = matcher.start();
            Integer endIndex = matcher.end();
            String cqCodeStr = matcher.group(1);
            String originCqCode = "[CQ:" + cqCodeStr + "]";
            String[] parts = cqCodeStr.split(",");
            String type = parts[0];
            HashMap<String, String> args = new HashMap<>();
            for (int i = 1; i < parts.length; i++) {
                String[] argParts = parts[i].split("=");
                StringBuilder argPart2 = new StringBuilder();
                for (int j = 1; j < argParts.length; j++) {
                    argPart2.append(argParts[j]);
                    if (j != argParts.length - 1){
                        argPart2.append("=");
                    }
                }
                args.put(argParts[0], argPart2.toString());
            }
            cqCodes.add(new CQCode(type, args, originCqCode, startIndex, endIndex));
        }
        return cqCodes;
    }
}