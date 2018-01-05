package cn.howso.deeplan.framework.shiro;


import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;
/**
 * 拓展shiro的WildcardPermission，增强权限字符串匹配功能，增加使用**匹配多级字符串的模式。
 * eg：a:b:c可以匹配a:**
 *    a:b:c:d 可以匹配a:**:d
 *    a:b:c:d 可以匹配a:**
 *    a:b:c:d 可以匹配a:*:**
 *    a:b:c:d 可以匹配a:**:*
 *    a:b:c:d:e可以匹配a:**:d:**
 *    a:b:c:d可以匹配a:**:b:c:d
 *    
 * */
public class MyWildcardPermission extends WildcardPermission{
    private static final long serialVersionUID = 1L;
    protected static final String MY_WILDCARD_TOKEN = "**";
    protected MyWildcardPermission() {
        super();
    }

    public MyWildcardPermission(String wildcardString) {
        this(wildcardString, DEFAULT_CASE_SENSITIVE);
    }

    public MyWildcardPermission(String wildcardString, boolean caseSensitive) {
        setParts(wildcardString, caseSensitive);
    }
    public boolean implies(Permission p) {
        // By default only supports comparisons with other WildcardPermissions
        if (!(p instanceof MyWildcardPermission)) {
            return false;
        }

        MyWildcardPermission wp = (MyWildcardPermission) p;

        List<Set<String>> otherParts = wp.getParts();//执行需要的权限集合

        int partIndex = 0;
        int otherIndex = 0;
        List<Set<String>> parts = getParts();//拥有的权限
        if(!otherParts.isEmpty()){
            while(true){
                if(partIndex >= parts.size()){//如果 parts用完了，就返回true
                    return true;
                }
                if(otherIndex>=otherParts.size()){//如果otherParts用完了，就跳出循环，判断parts之后的部分。
                    break;
                }
                Set<String> otherPart = otherParts.get(otherIndex);//当前otherPart
                Set<String> part = parts.get(partIndex);//当前part
                if(!part.contains(WILDCARD_TOKEN) && !part.containsAll(otherPart)){//如果part既不是通配符*，又不全部包含otherPart，就判断是否包含**
                    if(part.contains(MY_WILDCARD_TOKEN)){
                        partIndex = partIndex+1;//如果**后面没有part了，就说明parts用完了，返回true
                        if(partIndex >= parts.size()){
                            return true;
                        }
                        part = parts.get(partIndex);//取**后面的part
                        for(;otherIndex<otherParts.size();otherIndex++){//遍历otherParts当前与之后的otherPart，与**后面的part匹配
                            otherPart = otherParts.get(otherIndex);
                            if(part.containsAll(otherPart)){//匹配上了就跳出for循环，继续匹配
                                break;
                            }
                        }
                        if(otherIndex==otherParts.size()){//如果otherParts用完了，就跳出循环，判断parts之后的部分。
                            break;
                        }
                    }else{
                        return false;
                    }
                }
                partIndex++;
                otherIndex++;
            }
        }

        // If this permission has more parts than the other parts, only imply it if all of the other parts are wildcards
        for (; partIndex < getParts().size(); partIndex++) {
            Set<String> part = getParts().get(partIndex);
            if (!part.contains(WILDCARD_TOKEN) && !part.contains(MY_WILDCARD_TOKEN)) {
                return false;
            }
        }

        return true;
    }
}
