
package cn.howso.deeplan.framework.model;

import java.util.List;

/**
 * <p>
 * mybatis的分页插件需要的model
 * </p>
 * 
 * @author wzf
 * @date 2016年3月15日 上午9:16:24
 */
public class Page<T> {

    private int offset = 0;
    private int limit = Integer.MAX_VALUE;
    private int total = -1;
    private List<T> rows;
    
    public int getTotal() {
        return total;
    }
    
    public void setTotal(int total) {
        this.total = total;
    }
    
    public List<T> getRows() {
        return rows;
    }
    
    public void setRows(List<T> rows) {
        this.rows = rows;
    }
    public Page() {
    }
    public <E> Page<E> fromPageNumSize(int pageNum,int size,Class<E> clazz){
        int limit = size;
        int offset = (pageNum-1)*size;
        return new Page<E>(limit,offset);
    }
    public <E> Page<E> fromLimitOffset(int limit,int offset,Class<E> clazz){
        return new Page<E>(limit,offset);
    }
    public Page(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public final int getOffset() {
        return offset;
    }

    public final void setOffset(int offset) {
        this.offset = offset;
    }

    public final int getLimit() {
        return limit;
    }

    public final void setLimit(int limit) {
        this.limit = limit;
    }
}
