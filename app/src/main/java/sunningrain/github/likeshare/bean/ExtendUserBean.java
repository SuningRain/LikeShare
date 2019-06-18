package sunningrain.github.likeshare.bean;

/**
 * Created by 27837 on  2019/5/3.
 */
public class ExtendUserBean {
    private Integer worksCount;//作品数
    private Integer collectionCount;//关注数
    private Integer funsCount;//粉丝数

    public Integer getWorksCount() {
        return worksCount;
    }

    public void setWorksCount(Integer worksCount) {
        this.worksCount = worksCount;
    }

    public Integer getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(Integer collectionCount) {
        this.collectionCount = collectionCount;
    }

    public Integer getFunsCount() {
        return funsCount;
    }

    public void setFunsCount(Integer funsCount) {
        this.funsCount = funsCount;
    }
}
