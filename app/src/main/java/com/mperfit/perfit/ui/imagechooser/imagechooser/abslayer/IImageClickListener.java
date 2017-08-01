package com.mperfit.perfit.ui.imagechooser.imagechooser.abslayer;


import com.mperfit.perfit.ui.imagechooser.imagechooser.album.ImageInfo;

import java.util.List;

/**
 * 点击图片效果
 */
public interface IImageClickListener {

    //返回值为是否阻止默认操作，默认会增加选中
    boolean onAdd(List<ImageInfo> data, ImageInfo info);
    //返回值为是否阻止默认操作，默认会取消选中
    boolean onCancel(List<ImageInfo> data, ImageInfo info);

}
