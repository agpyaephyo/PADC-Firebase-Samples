package xyz.aungpyaephyo.padc.firebase.events;

import java.util.List;

import xyz.aungpyaephyo.padc.firebase.data.vo.NewsFeedVO;

/**
 * Created by aung on 8/18/17.
 */

public class FirebaseEvents {

    public static class NewsFeedLoadedEvent {
        private List<NewsFeedVO> newsFeed;

        public NewsFeedLoadedEvent(List<NewsFeedVO> newsFeed) {
            this.newsFeed = newsFeed;
        }

        public List<NewsFeedVO> getNewsFeed() {
            return newsFeed;
        }
    }
}
