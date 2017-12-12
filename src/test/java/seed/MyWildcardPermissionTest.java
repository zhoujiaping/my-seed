package seed;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.shiro.authz.Permission;

public class MyWildcardPermissionTest {
    public static final String WILDCARD_TOKEN = "*";
    public static final String MY_WILDCARD_TOKEN = "**";
    public static void main(String[] args) {
        String other = "user,role:id:a:query:ppp:c:d";
        other = "a:b:c";
        String perm = "user:*:query";
        perm = "";
        perm = "user,role,perm:**:a:**:*";
        perm = "a:**:b:**:c:**";
        test(other,perm);
    }
    public static void test(String other,String perm){
        System.out.println(other);
        System.out.println(perm);
        System.out.println(implies(other, perm));
        System.out.println();
    }
    private static List<Set<String>> parsePermPart(String perm){
        List<Set<String>> list = Arrays.asList(perm.split(":")).stream()
                .map(part->{
                    List<String> arr = Arrays.asList(part.split(","));
                    return arr.stream().filter(s->s.length()>0).collect(Collectors.toSet());
                }).filter(s->!s.isEmpty()).collect(Collectors.toList());
        return list;
    }
    public static boolean implies(String other,String perm) {
        List<Set<String>> otherParts = parsePermPart(other);//执行需要的权限集合

        int partIndex = 0;
        int otherIndex = 0;
        List<Set<String>> parts = parsePermPart(perm);
        if(!otherParts.isEmpty()){
            while(true){
                if(partIndex >= parts.size()){
                    return true;
                }
                if(otherIndex>=otherParts.size()){
                    break;
                }
                Set<String> otherPart = otherParts.get(otherIndex);
                Set<String> part = parts.get(partIndex);
                if(!part.contains(WILDCARD_TOKEN) && !part.containsAll(otherPart)){
                    if(part.contains(MY_WILDCARD_TOKEN)){
                        partIndex = partIndex+1;
                        if(partIndex >= parts.size()){
                            return true;
                        }
                        part = parts.get(partIndex);
                        for(;otherIndex<otherParts.size();otherIndex++){
                            otherPart = otherParts.get(otherIndex);
                            if(part.containsAll(otherPart)){
                                break;
                            }
                        }
                        if(otherIndex==otherParts.size()){
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
        for (; partIndex < parts.size(); partIndex++) {
            Set<String> part = parts.get(partIndex);
            if (!part.contains(WILDCARD_TOKEN) && !part.contains(MY_WILDCARD_TOKEN)) {
                return false;
            }
        }

        return true;
    }
}
