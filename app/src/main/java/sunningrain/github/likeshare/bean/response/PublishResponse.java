package sunningrain.github.likeshare.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 27837 on  2019/5/3.
 */
public class PublishResponse implements Serializable {

    private static final long serialVersionUID = 6127306333424946420L;
    /**
     * data : [{"publishId":7,"uId":5,"publishTime":"2019-05-04 22:58:24","publishText":"在这里贴一张图","publishPic":"5_1556981904038.jpg"},{"publishId":8,"uId":5,"publishTime":"2019-05-04 23:08:27","publishText":"在这里贴一张图","publishPic":"5_1556982507214.JPG"},{"publishId":9,"uId":5,"publishTime":"2019-05-04 23:37:54","publishText":"在这里贴一张图","publishPic":"5_1556984274465.jpg"},{"publishId":10,"uId":5,"publishTime":"2019-05-04 23:38:29","publishText":"在这里贴一张图","publishPic":"5_1556984309240.jpg"},{"publishId":11,"uId":5,"publishTime":"2019-05-05 17:00:06","publishText":"在这里贴一张图","publishPic":"5_1557046806306.jpg"},{"publishId":12,"uId":5,"publishTime":"2019-05-05 18:37:21","publishText":"在这里贴一张图","publishPic":"5_1557052641637.jpg"},{"publishId":13,"uId":5,"publishTime":"2019-05-05 18:40:49","publishText":"在这里贴一张图","publishPic":"5_1557052849258.jpg"},{"publishId":15,"uId":5,"publishTime":"2019-05-06 10:18:44","publishText":"在这里贴一张图","publishPic":"5_1557109123897.jpg"}]
     * pageNo : 0
     * isLastPage : true
     * count : 8
     * isFristPage : true
     * pageSize : 10
     */

    private int pageNo;
    private boolean isLastPage;
    private int count;
    private boolean isFristPage;
    private int pageSize;
    private List<DataBean> data;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isIsFristPage() {
        return isFristPage;
    }

    public void setIsFristPage(boolean isFristPage) {
        this.isFristPage = isFristPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private static final long serialVersionUID = 9109559179411811500L;
        /**
         * publishId : 7
         * uId : 5
         * publishTime : 2019-05-04 22:58:24
         * publishText : 在这里贴一张图
         * publishPic : 5_1556981904038.jpg
         */

        private int publishId;
        private int uId;
        private String publishTime;
        private String publishText;
        private String publishPic;

        public int getPublishId() {
            return publishId;
        }

        public void setPublishId(int publishId) {
            this.publishId = publishId;
        }

        public int getUId() {
            return uId;
        }

        public void setUId(int uId) {
            this.uId = uId;
        }

        public String getPublishTime() {
            return publishTime;
        }

        public void setPublishTime(String publishTime) {
            this.publishTime = publishTime;
        }

        public String getPublishText() {
            return publishText;
        }

        public void setPublishText(String publishText) {
            this.publishText = publishText;
        }

        public String getPublishPic() {
            return publishPic;
        }

        public void setPublishPic(String publishPic) {
            this.publishPic = publishPic;
        }
    }
}
