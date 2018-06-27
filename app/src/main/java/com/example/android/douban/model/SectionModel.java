package com.example.android.douban.model;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.util.List;

/**
 * Created by Derrick on 2018/6/20.
 */

public class SectionModel extends SectionEntity<MovieReponse.Subjects> {

    private List<AdBean> mAdBeanList;

    public List<AdBean> getAdBeanList() {
        return mAdBeanList;
    }

    public void setAdBeanList(List<AdBean> adBeanList) {
        mAdBeanList = adBeanList;
    }

    public SectionModel(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public SectionModel(MovieReponse.Subjects subjects){
        super(subjects);
    }


}
