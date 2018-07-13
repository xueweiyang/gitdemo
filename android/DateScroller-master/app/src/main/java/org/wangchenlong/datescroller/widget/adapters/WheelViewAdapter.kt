package org.wangchenlong.datescroller.widget.adapters

import android.database.DataSetObserver
import android.view.View
import android.view.ViewGroup

import org.wangchenlong.datescroller.widget.config.ScrollerConfig

/**
 * 滚轮列表视图的适配器接口
 */
interface WheelViewAdapter {
    /**
     * 获取滚轮项目的数量
     *
     * @return 滚轮项目数量
     */
    val itemsCount: Int

    /**
     * 获取滚轮的配置
     *
     * @return 配置
     */
    /**
     * 设置滚轮的配置
     *
     * @param config 配置
     */
    var config: ScrollerConfig

    /**
     * 根据索引获取展示项目的视图.
     *
     * @param index       索引
     * @param convertView 复用视图
     * @param parent      依附的父视图
     * @return 当前视图
     */
    fun getItem(index: Int, convertView: View, parent: ViewGroup): View

    /**
     * 获取一个空的滚轮视图, 在开始或者结尾
     *
     * @param convertView 复用视图
     * @param parent      依附的父视图
     * @return 空视图
     */
    fun getEmptyItem(convertView: View, parent: ViewGroup): View

    /**
     * 注册数据集的观察者, 供适配器使用
     *
     * @param observer 被注册观察者
     */
    fun registerDataSetObserver(observer: DataSetObserver)

    /**
     * 取消已注册数据集的观察者
     *
     * @param observer 被取消的观察者
     */
    fun unregisterDataSetObserver(observer: DataSetObserver)
}
