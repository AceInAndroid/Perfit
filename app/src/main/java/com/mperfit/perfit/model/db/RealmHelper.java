//package com.mperfit.perfit.model.db;
//
//import android.content.Context;
//
//
//import com.mperfit.perfit.model.bean.ReadStateBean;
//import com.mperfit.perfit.model.bean.RealmLikeBean;
//import com.orhanobut.logger.Logger;
//
//import java.util.List;
//
//
//
//import static android.R.attr.data;
//
///**
// * Created by zhangbing on 16/11/08.
// */
//
//public class RealmHelper {
//
//    private static final String DB_NAME = "perfit.realm";
//
//    private Realm mRealm;
//
//    public RealmHelper(Context mContext) {
//        Realm.init(mContext);
//        RealmConfiguration config = new RealmConfiguration.Builder()
//                .name(DB_NAME) //文件名
//                .schemaVersion(1) //版本号
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        mRealm = Realm.getInstance(config);
//    }
//
//    /**
//     * 增加 阅读记录
//     * @param id
//     */
//    public void insertNewsId(int id) {
//        mRealm.beginTransaction();
//        ReadStateBean bean = mRealm.createObject(ReadStateBean.class);
//        bean.setId(id);
//        mRealm.commitTransaction();
//    }
//
//    /**
//     * 查询 阅读记录
//     * @param id
//     * @return
//     */
//    public boolean queryNewsId(int id) {
//        RealmResults<ReadStateBean> results = mRealm.where(ReadStateBean.class).findAll();
//        for(ReadStateBean item : results) {
//            if(item.getId() == id) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 增加 收藏记录
//     * @param bean
//     */
//    public void insertLikeBean(final RealmLikeBean bean) {
//        mRealm.beginTransaction();
//        mRealm.copyToRealm(bean);
//        mRealm.commitTransaction();
//
//    }
//
//    /**
//     * 删除 收藏记录
//     * @param id
//     */
//    public void deleteLikeBean(final String id) {
//        final RealmLikeBean data = mRealm.where(RealmLikeBean.class).equalTo("id",id).findFirst();
//        mRealm.beginTransaction();
//        data.deleteFromRealm();
//        mRealm.commitTransaction();
//
//    }
//
//    /**
//     * 删除 所有记录
//     * @param
//     */
//    public void deleteAllLikeBean() {
//        RealmResults<RealmLikeBean> all = mRealm.where(RealmLikeBean.class).findAll();
//        Logger.e("所有收藏："+all.size()+"");
//        mRealm.beginTransaction();
//        for ( int i = 0 ;i < all.size();i++){
//            RealmLikeBean realmLikeBean = all.get(i);
//            realmLikeBean.deleteFromRealm();
//            Logger.e("删除收藏第："+ i +"个");
//
//        }
//        mRealm.commitTransaction();
//    }
//
//
//
//
//    /**
//     * 查询 收藏记录
//     * @param id
//     * @return
//     */
//    public boolean queryLikeId(String id) {
//        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAll();
//        for(RealmLikeBean item : results) {
//            if(item.getId().equals(id)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public List<RealmLikeBean> getLikeList() {
//        //使用findAllSort ,先findAll再result.sort无效
//        RealmResults<RealmLikeBean> results = mRealm.where(RealmLikeBean.class).findAllSorted("time");
//        return mRealm.copyFromRealm(results);
//    }
//
//    /**
//     * 修改 收藏记录 时间戳以重新排序
//     * @param id
//     * @param time
//     * @param isPlus
//     */
//    public void changeLikeTime(String id ,long time, boolean isPlus) {
//        RealmLikeBean bean = mRealm.where(RealmLikeBean.class).equalTo("id", id).findFirst();
//        mRealm.beginTransaction();
//        if (isPlus) {
//            bean.setTime(++time);
//        } else {
//            bean.setTime(--time);
//        }
//        mRealm.commitTransaction();
//    }
//}
