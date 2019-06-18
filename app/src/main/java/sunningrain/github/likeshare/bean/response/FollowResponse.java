package sunningrain.github.likeshare.bean.response;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 27837 on  2019/5/5.
 */
public class FollowResponse implements Serializable {

    private static final long serialVersionUID = -8137784668948087920L;
    /**
     * data : [{"publishId":7,"uId":5,"uPhoto":"5_1556952081467.jpg","uName":"张雨","publishTime":"2019-05-04 22:58:24","publishText":"在这里贴一张图","publishPic":"5_1556981904038.jpg"},{"publishId":8,"uId":5,"uPhoto":"5_1556952081467.jpg","uName":"张雨","publishTime":"2019-05-04 23:08:27","publishText":"在这里贴一张图","publishPic":"5_1556982507214.JPG"},{"publishId":9,"uId":5,"uPhoto":"5_1556952081467.jpg","uName":"张雨","publishTime":"2019-05-04 23:37:54","publishText":"在这里贴一张图","publishPic":"5_1556984274465.jpg"},{"publishId":10,"uId":5,"uPhoto":"5_1556952081467.jpg","uName":"张雨","publishTime":"2019-05-04 23:38:29","publishText":"在这里贴一张图","publishPic":"5_1556984309240.jpg"}]
     * pageNo : 0
     * isLastPage : true
     * count : 4
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
        private static final long serialVersionUID = -2669338960188987037L;
        /**
         * publishId : 7
         * uId : 5
         * uPhoto : 5_1556952081467.jpg
         * uName : 张雨
         * publishTime : 2019-05-04 22:58:24
         * publishText : 在这里贴一张图
         * publishPic : 5_1556981904038.jpg
         */

        private int publishId;
        private int uId;
        private String uPhoto;
        private String uName;
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

        public String getUPhoto() {
            return uPhoto;
        }

        public void setUPhoto(String uPhoto) {
            this.uPhoto = uPhoto;
        }

        public String getUName() {
            return uName;
        }

        public void setUName(String uName) {
            this.uName = uName;
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
