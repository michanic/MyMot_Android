package ru.michanic.mymot.Models;

import java.util.List;

public class ParseAdvertsResult {
    final List<Advert> adverts;
    final boolean more;

    public ParseAdvertsResult(List<Advert> adverts, boolean more) {
        this.adverts = adverts;
        this.more = more;
    }
    public List<Advert> getAdvetrs() {
        return adverts;
    }
    public boolean loadMore() {
        return more;
    }
}