package ru.michanic.mymot.Models;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import ru.michanic.mymot.Enums.SourceType;

class ParseAdvertsResult {
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

public class HtmlParser {

    public ParseAdvertsResult parseAdverts(Document document, SourceType sourceType) {



        return new ParseAdvertsResult(new ArrayList(), false);
    }

}
