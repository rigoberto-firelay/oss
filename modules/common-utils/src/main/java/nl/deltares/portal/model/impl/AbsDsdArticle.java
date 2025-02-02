package nl.deltares.portal.model.impl;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import nl.deltares.portal.model.DsdArticle;
import nl.deltares.portal.utils.DsdParserUtils;
import nl.deltares.portal.utils.DuplicateCheck;
import nl.deltares.portal.utils.JsonContentUtils;
import nl.deltares.portal.utils.XmlContentUtils;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public abstract class AbsDsdArticle implements DsdArticle {

    private final JournalArticle article;
    public final long instantiationTime;
    protected final DsdParserUtils dsdParserUtils;
    private final Locale locale;
    private Document document;


    @Override
    public void validate() throws PortalException {
        //
    }

    @Override
    public String getStructureKey() {
        return DSD_STRUCTURE_KEYS.Generic.name();
    }

    @Override
    public long getResourceId() {
        if (article == null) return 0;
        return article.getResourcePrimKey();
    }

    @Override
    public String getArticleId() {
        if (article == null) return "0";
        return article.getArticleId();
    }

    @Override
    public String getTitle() {
        if (article == null) return "";
        return article.getTitle();
    }

    @Override
    public long getGroupId(){
        if (article == null) return 0;
        return article.getGroupId();
    }

    @Override
    public long getCompanyId(){
        if (article == null) return 0;
        return article.getCompanyId();
    }

    @Override
    public Document getDocument(){
        return document;
    }

    AbsDsdArticle(){
        this.article = null;
        this.instantiationTime = System.currentTimeMillis();
        this.dsdParserUtils = null;
        this.locale = null;
    }

    AbsDsdArticle(JournalArticle article, DsdParserUtils dsdParserUtils, Locale locale) throws PortalException {
        this.article = article;
        this.instantiationTime = System.currentTimeMillis();
        this.dsdParserUtils = dsdParserUtils;
        this.locale = locale;
        init();
    }

    private void init() throws PortalException {
        this.document = XmlContentUtils.parseContent(article, locale);
    }

    public String getSmallImageURL(ThemeDisplay themeDisplay) {
        if (article == null) return "";
        String url = article.getSmallImageURL();
        if (Validator.isNull(url)) {
            url = article.getArticleImageURL(themeDisplay);
        }
        if (url == null) return "";
        return url;
    }

    List<Room> parseRooms(String[] roomReferences) throws PortalException {

        DuplicateCheck check = new DuplicateCheck();
        ArrayList<Room> rooms = new ArrayList<>();
        for (String json : roomReferences) {
            JournalArticle article = JsonContentUtils.jsonReferenceToJournalArticle(json);
            AbsDsdArticle room = dsdParserUtils.toDsdArticle(article, this.locale);
            if (!(room instanceof Room)) throw new PortalException(String.format("Article %s not instance of Room", article.getTitle()));
            if (check.checkDuplicates(room)) rooms.add((Room) room);
        }
        return rooms;
    }

    public JournalArticle getJournalArticle(){
        return article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbsDsdArticle that = (AbsDsdArticle) o;
        return article != null && article.getPrimaryKey() == that.article.getPrimaryKey();
    }

    @Override
    public int hashCode() {
        if (article == null) return 0;
        return Objects.hash(article.getPrimaryKey());
    }

    public Locale getLocale() {
        return locale;
    }
}
