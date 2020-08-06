package com.wag.gamebox.entity;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by James on 2018/10/22.
 * 搜索历史
 */
@Table(name = "QueryHistory")
public class QueryHistory {

    @Column(name = "id", isId = true, autoGen = true)
    private int id;

    @Column(name = "keyword")
    private String keyWord;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
