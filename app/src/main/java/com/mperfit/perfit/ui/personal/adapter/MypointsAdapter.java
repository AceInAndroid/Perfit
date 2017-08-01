package com.mperfit.perfit.ui.personal.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mperfit.perfit.R;
import com.mperfit.perfit.model.bean.MyPointsListBean;
import com.mperfit.perfit.utils.DateUtil;

import java.util.List;

/**
 * Created by zhangbing on 2017/3/17.
 */

public class MypointsAdapter extends BaseQuickAdapter<MyPointsListBean.DataBean.ListBean,BaseViewHolder> {
    public MypointsAdapter(int layoutResId, List<MyPointsListBean.DataBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyPointsListBean.DataBean.ListBean item) {
        helper.setText(R.id.tv_data,DateUtil.formatTimeToString(item.getCreate_time()))
        .setText(R.id.tv_address,item.getContent())
        .setText(R.id.tv_score,item.getScore()+"");

    }

}
