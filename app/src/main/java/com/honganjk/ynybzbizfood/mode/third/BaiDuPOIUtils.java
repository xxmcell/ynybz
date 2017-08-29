
package com.honganjk.ynybzbizfood.mode.third;


import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.honganjk.ynybzbizfood.utils.other.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.honganjk.ynybzbizfood.mode.javabean.base.UserInfo.userData;

/**
 * 说明:百度POI检索
 * 360621904@qq.com 杨阳 2017/3/9  10:21
 */
public abstract class BaiDuPOIUtils {
    /**
     * @param city    要查寻的城市
     * @param contetn 要查寻的内容
     */
    public BaiDuPOIUtils(String city, String contetn) {
        search(city, contetn);
    }

    /**
     * @param contetn 要查寻的内容
     */
    public BaiDuPOIUtils(String contetn) {
        search(contetn);
    }

    /**
     * @param city    要查寻的城市
     * @param contetn 要查寻的内容
     */
    public void search(final String city, String contetn) {
        // 第一步，创建在线建议查询实例；
        final SuggestionSearch mSuggestionSearch = SuggestionSearch.newInstance();
        //第二步，创建在线建议查询监听者
        OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
            public void onGetSuggestionResult(SuggestionResult res) {
                List<SuggestionResult.SuggestionInfo> datas = new ArrayList<>();
                //获取在线建议检索结果
                if (res != null && res.getAllSuggestions() != null) {
                    datas.addAll(res.getAllSuggestions());
                    for (int i = 0; i < datas.size(); i++) {
                        if (datas.get(i).pt == null || StringUtils.isBlank(datas.get(i).district)
                                || StringUtils.isBlank(datas.get(i).key) || !datas.get(i).city.contains(city)) {
                            datas.remove(i);
                            --i;
                        }
                    }
                    succeed(datas);
                    return;
                }
                succeed(datas);
                //第五步，释放在线建议查询实例；
                mSuggestionSearch.destroy();
            }
        };
        //第三步，设置在线建议查询监听者；
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        //第四步，发起在线建议查询；// 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
        mSuggestionSearch.requestSuggestion((new SuggestionSearchOption())
                .keyword(contetn)
                .city(city));
    }

    /**
     * @param contetn 要查寻的内容
     */
    public void search(String contetn) {
        search(userData.getCurrentCity(), contetn);
    }

    /**
     * 检索成成功的回调
     */
    public abstract void succeed(List<SuggestionResult.SuggestionInfo> datas);


}