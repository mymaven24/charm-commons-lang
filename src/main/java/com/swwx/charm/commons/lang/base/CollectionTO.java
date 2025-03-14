package com.swwx.charm.commons.lang.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CollectionTO<T> extends BasicTO {

    private static final long serialVersionUID = -4527396957302778534L;

    /**
     * 一页多少条
     */
    private int pageSize;

    /**
     * 总页数
     */
    private int pageCnt;

    /**
     * 总条数
     */
    private long recordCnt;

    private List<T> list = new ArrayList<T>();

    public CollectionTO() {
        super();
    }

    public CollectionTO(List<T> list, long recordCnt, int pageSize) {
        super();
        this.pageSize = pageSize;
        if (pageSize > 0) {
            pageCnt = (int)Math.ceil((double)recordCnt / (double)pageSize);
        }
        this.recordCnt = recordCnt;
        if (list != null){
            this.list = list;
        }
    }

    public void add(T t) {
        list.add(t);
    }

    public void add(T t, int pos) {
        list.add(pos, t);
    }

    public void addAll(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public T get(int index) {
        return list.get(index);
    }

    public int size() {
        return list.size();
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCnt() {
        return pageCnt;
    }

    public void setPageCnt(int pageCnt) {
        this.pageCnt = pageCnt;
    }

    public long getRecordCnt() {
        return recordCnt;
    }

    public void setRecordCnt(long recordCnt) {
        this.recordCnt = recordCnt;
    }

    public void sort(Comparator<T> c) {
        Collections.sort(this.list, c);
    }

    public void merge(List<T> list2, Comparator<T> c) {
        merge(list2, c, false);
    }

    public void merge(List<T> list2, Comparator<T> c, boolean append) {
        for (T t2: list2) {
            int index = indexOf(t2, c);
            if (index > -1) {
                list.remove(index);
                list.add(index, t2);
            } else {
                if (append) {
                    list.add(t2);
                }
            }
        }
    }

    public int indexOf(T t2, Comparator<T> c) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            T t1 = list.get(i);
            if (c.compare(t1, t2) == 0) {
                return i;
            }
        }
        return -1;
    }
}